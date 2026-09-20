package com.whereisthesolution.whereisihesolutionapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.icon_visible


@Preview(showBackground = true)
@Composable
fun CustomModalNavigationDrawer(
    ownerImage: String = "image_link",
    ownerName: String = "Matheus Bento Vieira",
    ownerCity: String = "Juazeiro do Norte",
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable (() -> Unit) = {}
) {

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                // Cabeçalho do usuário
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.icon_visible),
                        contentDescription = "owner_image"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = ownerName
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = ownerCity
                    )
                }

                HorizontalDivider()

                // Opções
                NavigationDrawerItem(
                    label = { Text("Minha conta") },
                    selected = false,
                    onClick = {
                        // abrir conta
                    },
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.icon_visible),
                            contentDescription = "Minha conta"
                        )
                    },
                )

                NavigationDrawerItem(
                    label = { Text("Dados pessoais") },
                    selected = false,
                    onClick = {
                        // abrir dados pessoais
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Configurações") },
                    selected = false,
                    onClick = {
                        // abrir configurações
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Sair") },
                    selected = false,
                    onClick = {
                        // logout
                    }
                )
            }
        },
        drawerState = drawerState
    ) {
        content()
    }

}