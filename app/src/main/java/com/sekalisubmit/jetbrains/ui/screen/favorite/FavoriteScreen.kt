package com.sekalisubmit.jetbrains.ui.screen.favorite

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sekalisubmit.jetbrains.R
import com.sekalisubmit.jetbrains.di.Injection
import com.sekalisubmit.jetbrains.model.FavsIDE
import com.sekalisubmit.jetbrains.ui.ViewModelFactory
import com.sekalisubmit.jetbrains.ui.common.UIState
import com.sekalisubmit.jetbrains.ui.component.ItemLayout
import com.sekalisubmit.jetbrains.ui.theme.jetFont

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {

    viewModel.uiState.collectAsState(initial = UIState.Loading).value.let { uiState ->
        when (uiState) {
            is UIState.Loading -> {
                viewModel.getAllFavIDE()
            }
            is UIState.Success -> {
                FavoriteContent(
                    favsIDE = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }
            is UIState.Error -> {
                Log.e("FavoriteScreen", uiState.errorMessage)
                Text(
                    text = uiState.errorMessage,
                    fontFamily = jetFont,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
fun FavoriteContent(
    favsIDE: List<FavsIDE>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxSize()
            .testTag("favoriteScreen")
    ) {
        item {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp)
            ){
                Text(
                    text = "Favorite(s)",
                    fontFamily = jetFont,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        if (favsIDE.isNotEmpty()) {
            item {
                Column(
                    modifier = modifier.testTag("favAvailable")
                ){
                    for (fav in favsIDE) {
                        ItemLayout(
                            image = fav.ide.image,
                            title = fav.ide.title,
                            subtitle = fav.ide.subtitle,
                            modifier = modifier
                                .clickable{
                                    navigateToDetail(fav.ide.id)
                                }
                                .testTag("IDE_${fav.ide.id}")
                        )
                    }
                }
            }
        } else {
            item {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp)
                        .testTag("favUnavailable")
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_nofav),
                        contentDescription = "No Data",
                        modifier = modifier.fillMaxSize()
                    )

                    Text(
                        text = "No Favorite",
                        color = MaterialTheme.colorScheme.outline,
                        fontFamily = jetFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}