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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.whereisthesolution.whereisihesolutionapp.animated.OfflineAnimation
import com.whereisthesolution.whereisihesolutionapp.font.interFontFamily
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home.HomeUiState
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.assignment_now
import whereisthesolution.app.shared.generated.resources.icon_favorite
import whereisthesolution.app.shared.generated.resources.icon_share
import whereisthesolution.app.shared.generated.resources.like_icon
import whereisthesolution.app.shared.generated.resources.location_city_icon
import whereisthesolution.app.shared.generated.resources.person_pessoal_icon

@Preview(showBackground = true)
@Composable
fun FeedCity(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    commentsLength: Int = 1
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(uiState.posts, key = { it.post.id }) { feedPost ->
            MockPostage(
                userOwnerName = feedPost.author?.name ?: "Usuário",
                userOwnerCity = feedPost.post.address,
                imageUrl = feedPost.post.imageUrls.firstOrNull().orEmpty(),
                title = feedPost.post.title,
                description = feedPost.post.description,
                commentsLength = commentsLength,
            )
        }
    }
}


@Composable
fun MockPostage(
    userOwnerName: String,
    userOwnerCity: String,
    imageUrl: String,
    title: String,
    description: String,
    commentsLength: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.person_pessoal_icon),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(18.dp),
                    tint = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userOwnerName,
                    fontFamily = interFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = userOwnerCity,
                    fontFamily = interFontFamily(),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = { },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_share),
                    contentDescription = "Mais opções",
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl.isNotEmpty()) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = "post_image",
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    },
                    error = {
                        OfflineAnimation()
                    }
                )
            }
        }

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
                    painter = painterResource(Res.drawable.like_icon),
                    contentDescription = "Curtir",
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_favorite),
                    contentDescription = "Favoritar",
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.location_city_icon),
                    contentDescription = "Ver no mapa",
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }



            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.assignment_now),
                    contentDescription = "Salvar",
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Curtidas
        Text(
            text = "1.248 curtidas",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontFamily = interFontFamily(),
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
                fontFamily = interFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = " $title",
                fontFamily = interFontFamily(),
                fontSize = 14.sp
            )
        }

        if (description.isNotBlank()) {
            Text(
                text = description,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontFamily = interFontFamily(),
                fontSize = 14.sp
            )
        }

        // Comentários
        Text(
            text = "Ver todos os $commentsLength comentários",
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
            color = Color.Gray,
            fontFamily = interFontFamily(),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
