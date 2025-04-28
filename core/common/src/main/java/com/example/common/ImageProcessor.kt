package com.example.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageProcessor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun compressUriToFile(uri: Uri, reqWidth: Int = 2560, quality: Int = 60): Result<File> =
        runCatching {
            val bitmap = decodeBitmapFromUri(uri)
                ?: throw IOException("Bitmap decoding failed")

            val rotatedBitmap = rotateBitmapIfRequired(bitmap, uri).getOrThrow()
            val resizedBitmap = resizeBitmapIfNeeded(rotatedBitmap, reqWidth)

            val (suffix, format) = getSuffixAndCompressFormatByVersion()
            val file = createTempImageFile(suffix)

            compressBitmapToFile(resizedBitmap, file, format, quality).getOrThrow()

            bitmap.recycle()
            if (bitmap != rotatedBitmap) rotatedBitmap.recycle()
            if (rotatedBitmap != resizedBitmap) resizedBitmap.recycle()

            file
        }

    private fun decodeBitmapFromUri(uri: Uri): Bitmap? {
        return context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        }
    }

    private suspend fun rotateBitmapIfRequired(bitmap: Bitmap, uri: Uri): Result<Bitmap> =
        runCatching {
            withContext(Dispatchers.IO) {
                val inputStream =
                    context.contentResolver.openInputStream(uri) ?: return@withContext bitmap
                val exif = ExifInterface(inputStream)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                val rotation = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                    else -> 0f
                }

                if (rotation == 0f) {
                    bitmap
                } else {
                    Bitmap.createBitmap(
                        bitmap, 0, 0, bitmap.width, bitmap.height,
                        Matrix().apply { postRotate(rotation) }, true
                    )
                }
            }
        }

    private fun resizeBitmapIfNeeded(bitmap: Bitmap, maxWidth: Int): Bitmap {
        if (bitmap.width <= maxWidth) return bitmap

        val scaleFactor = maxWidth.toFloat() / bitmap.width
        val newHeight = (bitmap.height * scaleFactor).toInt()

        return Bitmap.createScaledBitmap(bitmap, maxWidth, newHeight, true)
    }

    private fun compressBitmapToFile(
        bitmap: Bitmap,
        file: File,
        format: Bitmap.CompressFormat,
        quality: Int
    ): Result<Unit> = runCatching {
        FileOutputStream(file).use { out ->
            if (!bitmap.compress(format, quality, out)) {
                throw IOException("Failed to compress bitmap.")
            }
        }
    }

    private fun getSuffixAndCompressFormatByVersion(): Pair<String, Bitmap.CompressFormat> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ".webp" to Bitmap.CompressFormat.WEBP_LOSSY
        } else {
            ".jpeg" to Bitmap.CompressFormat.JPEG
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createTempImageFile(suffix: String): File {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            kotlin.io.path.createTempFile(
                directory = context.cacheDir.toPath(),
                prefix = FILE_NAME_PREFIX,
                suffix = suffix
            ).toFile()
        } else {
            File.createTempFile(FILE_NAME_PREFIX, suffix, context.cacheDir)
        }
    }

    companion object {
        private const val FILE_NAME_PREFIX = "upload_"
    }
}