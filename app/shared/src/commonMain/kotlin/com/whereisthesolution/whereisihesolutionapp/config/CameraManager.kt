package com.whereisthesolution.whereisihesolutionapp.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.whereisthesolution.whereisihesolutionapp.config.media.ImageResult

interface MediaPickerController {
    fun launchCamera()
    fun launchGallery()
}

@Composable
expect fun rememberPlatformMediaPicker(
    onImageSelected: (ImageResult) -> Unit
): MediaPickerController


expect fun decodeImage(bytes: ByteArray): ImageBitmap?