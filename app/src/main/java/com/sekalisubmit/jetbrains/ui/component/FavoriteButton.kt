package com.sekalisubmit.jetbrains.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import com.sekalisubmit.jetbrains.R

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit,
) {
    Row {
        IconButton(
            onClick = { onClick() },
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
                modifier = modifier
                    .testTag(if (isFavorite) "Favorited" else "Not Favorited")
            )
        }
    }
}