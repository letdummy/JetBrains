package com.sekalisubmit.jetbrains.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sekalisubmit.jetbrains.R
import com.sekalisubmit.jetbrains.ui.theme.jetFont


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ProvideTextStyle(
        value = TextStyle(
            fontFamily = jetFont,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        )
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                placeholder = {
                    Text(
                        stringResource(R.string.search),
                        color = Color.Black,
                        fontSize = 15.sp,
                    )
                },
                colors = SearchBarDefaults.colors(
                    Color.LightGray,
                    inputFieldColors = SearchBarDefaults.inputFieldColors(Color.Black)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("Search_Bar")
            ) {}
            
            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .size(40.dp)
                    .testTag("Icon_Favorite")
            ) {
                val icon: Painter = painterResource(id = R.drawable.ic_fav_fill)
                Icon(
                    painter = icon,
                    contentDescription = "Icon Bag Shopping",
                    tint = Color(0xFFFFAABB)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Image Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .align(Alignment.Bottom)
                    .clickable { }
                    .testTag("Icon_Avatar")
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(
        query = "",
        onQueryChange = {},
        navController = NavController(LocalContext.current)
    )
}