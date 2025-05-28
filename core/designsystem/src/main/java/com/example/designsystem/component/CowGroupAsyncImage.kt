package com.example.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cowgroup.core.designsystem.R

@Composable
fun CowGroupAsyncImage(
    modifier: Modifier = Modifier,
    imgUrl: String?,
    placeholder: Painter = painterResource(id = R.drawable.cowgroup_default_image),
    error: Painter = painterResource(id = R.drawable.cowgroup_default_image),
    contentDescription: String?,
    fallback: Painter = painterResource(id = R.drawable.cowgroup_default_image)
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .crossfade(true)
            .build(),
        modifier = modifier,
        placeholder = placeholder,
        error = error,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        fallback = fallback,
    )
}