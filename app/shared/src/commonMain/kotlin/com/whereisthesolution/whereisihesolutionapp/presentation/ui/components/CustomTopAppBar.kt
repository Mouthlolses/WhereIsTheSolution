package com.whereisthesolution.whereisihesolutionapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereisthesolution.whereisihesolutionapp.font.interFontFamily
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.account_icon
import whereisthesolution.app.shared.generated.resources.add_icon
import whereisthesolution.app.shared.generated.resources.icon_invisible
import whereisthesolution.app.shared.generated.resources.icon_visible


@Preview(showBackground = true)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    openUserModal: () -> Unit = {},
    openRegisterPost: () -> Unit = {}
) {
    Surface(
        shadowElevation = 16.dp
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Tá Errado!",
                    fontFamily = interFontFamily()
                )
            },
            modifier = modifier,
            navigationIcon = {
                IconButton(
                    onClick = {
                        openUserModal()
                    }
                ) {
                    Image(
                        painter = painterResource(Res.drawable.account_icon),
                        contentDescription = "icon_person_account",
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        openRegisterPost()
                    }
                ) {
                    Image(
                        painter = painterResource(Res.drawable.add_icon),
                        contentDescription = "icon_plus_create_post",
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        )
    }
}