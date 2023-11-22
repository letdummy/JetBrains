package com.sekalisubmit.jetbrains.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sekalisubmit.jetbrains.R

@Composable
fun FavoriteButton(
    ideId: Int,
    isFavorite: Boolean,
    onClick: (ideId: Int, isFavorite: Boolean) -> Unit,
) {
    Row {
        IconButton(
            onClick = { onClick(ideId, !isFavorite) }
        ) {
            val iconRes = if (isFavorite) {
                R.drawable.ic_fav_fill
            } else {
                R.drawable.ic_fav_default
            }

            Icon(
                painterResource(iconRes),
                contentDescription = if (isFavorite) "Add to favorite" else "Remove from favorite",
                tint = Color(0xFFFFAABB),
                modifier = Modifier
                    .testTag(if (isFavorite) "Favorited" else "Not Favorited")
            )
        }
    }
}


@Preview
@Composable
fun PreviewFavoriteButton() {
    FavoriteButton(
        ideId = 1,
        isFavorite = true
    ) { _, _ -> }
}