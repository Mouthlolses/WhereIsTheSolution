package com.whereisthesolution.whereisihesolutionapp.config

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.whereisthesolution.whereisihesolutionapp.config.media.ImageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

actual fun decodeImage(bytes: ByteArray): ImageBitmap? =
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        ?.asImageBitmap()

@Composable
actual fun rememberPlatformMediaPicker(
    onImageSelected: (ImageResult) -> Unit
): MediaPickerController {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap ?: return@rememberLauncherForActivityResult

        scope.launch {
            val bytes = withContext(Dispatchers.IO) {
                ByteArrayOutputStream().use { output ->
                    bitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        90,
                        output
                    )
                    output.toByteArray()
                }
            }

            onImageSelected(
                ImageResult(
                    bytes = bytes,
                    mimeType = "image/jpeg",
                    fileName = "post.jpg"
                )
            )
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        scope.launch {
            val result = withContext(Dispatchers.IO) {
                context.contentResolver
                    .openInputStream(uri)
                    ?.use { input ->
                        input.readBytes()
                    }
            }

            result?.let { bytes ->
                onImageSelected(
                    ImageResult(
                        bytes = bytes,
                        mimeType = context.contentResolver
                            .getType(uri) ?: "image/jpeg",
                        fileName = "post"
                    )
                )
            }
        }
    }

    return remember(cameraLauncher, galleryLauncher) {
        object : MediaPickerController {
            override fun launchCamera() {
                cameraLauncher.launch(null)
            }

            override fun launchGallery() {
                galleryLauncher.launch("image/*")
            }
        }
    }
}