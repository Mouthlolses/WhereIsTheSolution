package com.whereisthesolution.whereisihesolutionapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.icon_invisible
import whereisthesolution.app.shared.generated.resources.icon_visible

@Preview(showBackground = true)
@Composable
fun FeedCity(
    modifier: Modifier = Modifier,
    userOwnerName: String = "João",
    userOwnerCity: String = "Crato",
    commentsLength: Int = 1

) {
    LazyColumn(
        modifier = modifier
    ) {
        items(postList) {
            MockPostage(
                userOwnerName = userOwnerName,
                userOwnerCity = userOwnerCity,
                commentsLength = commentsLength
            )
        }
    }
}


@Composable
fun MockPostage(
    userOwnerName: String,
    userOwnerCity: String,
    commentsLength: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        // Header do post
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Avatar
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_visible),
                    contentDescription = "Avatar",
                    tint = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userOwnerName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = userOwnerCity,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_visible),
                    contentDescription = "Mais opções"
                )
            }
        }

        // Imagem do post
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.icon_visible),
                contentDescription = "Imagem do post",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
        }

        // Ações
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_visible),
                    contentDescription = "Curtir"
                )
            }

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_visible),
                    contentDescription = "Comentar"
                )
            }

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_visible),
                    contentDescription = "Compartilhar"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_visible),
                    contentDescription = "Salvar"
                )
            }
        }

        // Curtidas
        Text(
            text = "1.248 curtidas",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        // Legenda
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 6.dp,
                bottom = 12.dp
            )
        ) {
            Text(
                text = userOwnerName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = " Curtindo o Cariri! 🌴🔥",
                fontSize = 14.sp
            )
        }

        // Comentários
        Text(
            text = "Ver todos os ${commentsLength} comentários",
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
            color = Color.Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}


val postList = listOf(
    Post(
        id = 1,
        userOwner = "Carlos",
        userOwnerCity = "Juazeiro do Norte, CE",
        image = Res.drawable.icon_visible,
        iconsInteraction = listOf(
            "Like", "Comment"
        ),
        comments = listOf(
            "Gostei disso"
        )
    ),
    Post(
        id = 2,
        userOwner = "Maria",
        userOwnerCity = "Crato, CE",
        image = Res.drawable.icon_invisible,
        iconsInteraction = listOf(
            "Like", "Comment"
        ),
        comments = listOf(
            "Gostei disso",
            "Gostei Daquilo"
        )
    )
)

data class Post(
    val id: Long,
    val userOwner: String,
    val userOwnerCity: String,
    val image: DrawableResource,
    val iconsInteraction: List<String>,
    val comments: List<String>
)