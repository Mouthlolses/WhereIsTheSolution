package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.registerOccurrence

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereisthesolution.whereisihesolutionapp.config.decodeImage
import com.whereisthesolution.whereisihesolutionapp.config.media.ImageResult
import com.whereisthesolution.whereisihesolutionapp.config.rememberPlatformMediaPicker
import com.whereisthesolution.whereisihesolutionapp.font.interFontFamily
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.add_location_icon
import whereisthesolution.app.shared.generated.resources.arrow_back
import whereisthesolution.app.shared.generated.resources.image_placeholder_take_photo

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RegisterOccurrenceScreen(
    onBackScreen: () -> Unit = {},
    onPublish: () -> Unit = {},
    onAddLocation: () -> Unit = {},
) {

    var selectedImage by remember {
        mutableStateOf<ImageResult?>(null)
    }

    var description by remember {
        mutableStateOf("")
    }

    var locationName by remember {
        mutableStateOf<String?>(null)
    }

    val picker = rememberPlatformMediaPicker(
        onImageSelected = { image ->
            selectedImage = image
        }
    )

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 16.dp
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Nova Ocorrência",
                            fontFamily = interFontFamily(),
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackScreen()
                            },
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_back),
                                contentDescription = "return",
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            Surface(
                shadowElevation = 18.dp
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { },
                        shapes = ButtonDefaults.shapes(
                            shape = RoundedCornerShape(12.dp),
                            pressedShape = RoundedCornerShape(12.dp)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp, vertical = 22.dp)
                            .heightIn(min = 44.dp, max = 46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1769AA)
                        )
                    ) {
                        Text("Compartilhar", fontFamily = interFontFamily())
                    }
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    ) { paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Preview da foto depois da captura
            Card(
                onClick = { picker.launchCamera() },
                modifier = Modifier
                    .size(300.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (selectedImage == null) {
                        Image(
                            painter = painterResource(Res.drawable.image_placeholder_take_photo),
                            contentDescription = "place_holder_take_photo",
                            modifier = Modifier
                                .size(300.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        selectedImage?.let { image ->
                            val bitmap = remember(image) {
                                decodeImage(image.bytes)
                            }

                            bitmap?.let {
                                Image(
                                    bitmap = it,
                                    contentDescription = "Preview da publicação",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Escreva uma legenda...", fontFamily = interFontFamily()) },
                minLines = 3
            )

            OutlinedButton(
                onClick = onAddLocation,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.add_location_icon),
                    contentDescription = "add_location",
                    modifier = Modifier
                        .size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = locationName ?: "Adicionar localização",
                    fontFamily = interFontFamily()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterOccurrenceScreenPreview() {
    RegisterOccurrenceScreen()
}