package com.example.designsystem.component

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.common.ImageProcessor
import com.example.cowgroup.core.designsystem.R
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun CowGroupPhotoPicker(
    modifier: Modifier = Modifier,
    imageProcessor: ImageProcessor,
    imageUri: Uri? = null,
    updatePicture: (File) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(imageUri) {
        if (imageUri != null && imageUri != selectedImageUri) {
            selectedImageUri = imageUri
            val result = imageProcessor.compressUriToFile(imageUri)
            result.onSuccess { file -> updatePicture(file) }
                .onFailure { Log.e("photoPicker", "파일 생성 실패") }
        }
    }
    val photoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
                coroutineScope.launch {
                    val result = imageProcessor.compressUriToFile(uri)
                    result.onSuccess { file -> updatePicture(file) }
                        .onFailure { Log.e("photoPicker", "파일 생성 실패") }
                }
            }
        }
    Box(
        modifier = modifier
            .width(45.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }, contentAlignment = Alignment.Center
    ) {
        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = "selected_image"
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.baseline_camera_alt_24),
                    contentDescription = "icon_create_meeting_image"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "0/1",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}