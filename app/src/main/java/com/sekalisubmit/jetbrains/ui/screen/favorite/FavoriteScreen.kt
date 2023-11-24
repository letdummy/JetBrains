package com.sekalisubmit.jetbrains.ui.screen.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
            is UIState.Error -> {}
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
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp)
            ){
                Text(
                    text = "Favorite(s)",
                    fontFamily = jetFont,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (favsIDE.isNotEmpty()) {
            item {
                for (fav in favsIDE) {
                    ItemLayout(
                        image = fav.ide.image,
                        title = fav.ide.title,
                        subtitle = fav.ide.subtitle,
                        modifier = Modifier
                            .clickable{
                                navigateToDetail(fav.ide.id)
                            }
                    )
                }
            }
        } else {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_nofav),
                        contentDescription = "No Data",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FavoriteContentPreview() {
    FavoriteScreen(
        navigateToDetail = {}
    )
}