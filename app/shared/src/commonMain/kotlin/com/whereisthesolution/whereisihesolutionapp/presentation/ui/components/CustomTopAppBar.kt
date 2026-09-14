package com.whereisthesolution.whereisihesolutionapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.icon_invisible
import whereisthesolution.app.shared.generated.resources.icon_visible


@Preview(showBackground = true)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Voz Cidadã"
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = {}
            ) {
                Image(
                    painter = painterResource(Res.drawable.icon_visible),
                    contentDescription = "icon_person_account",
                    modifier = Modifier.size(16.dp),
                    contentScale = ContentScale.Crop
                )
            }
        },
        actions = {
            Image(
                painter = painterResource(Res.drawable.icon_invisible),
                contentDescription = "icon_plus_create_post",
                modifier = Modifier.size(16.dp),
                contentScale = ContentScale.Crop
            )
        }
    )
}