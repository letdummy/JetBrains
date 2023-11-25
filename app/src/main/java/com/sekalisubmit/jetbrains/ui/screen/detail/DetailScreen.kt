package com.sekalisubmit.jetbrains.ui.screen.detail

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sekalisubmit.jetbrains.R
import com.sekalisubmit.jetbrains.data.IDERepository
import com.sekalisubmit.jetbrains.di.Injection
import com.sekalisubmit.jetbrains.ui.ViewModelFactory
import com.sekalisubmit.jetbrains.ui.common.UIState
import com.sekalisubmit.jetbrains.ui.component.FavoriteButton
import com.sekalisubmit.jetbrains.ui.component.FeatureButton
import com.sekalisubmit.jetbrains.ui.theme.jetFont

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    ideId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UIState.Loading).value.let { uiState ->
        when (uiState) {
            is UIState.Loading -> {
                viewModel.getIDEbyId(ideId)
            }

            is UIState.Success -> {
                DetailContent(
                    ideID = uiState.data.ide.id,
                    image = uiState.data.ide.image,
                    title = uiState.data.ide.title,
                    subtitle = uiState.data.ide.subtitle,
                    description = uiState.data.ide.description,
                    ticker = uiState.data.ide.ticker,
                    navigateBack = navigateBack,
                )
            }

            is UIState.Error -> {
                Log.e("DetailScreen", uiState.errorMessage)
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
fun DetailContent(
    modifier: Modifier = Modifier,
    ideID: Long,
    image: Int,
    title: String,
    subtitle: String,
    description: String,
    ticker: String,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    var isFav by remember { mutableStateOf(IDERepository.getInstance().isFavIDE(ideID)) }
    val intentFeature = Intent(Intent.ACTION_VIEW, Uri.parse("https://ums.id/$ticker"))


    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .testTag("detailScreen")
            .fillMaxHeight()
    ) {

        // above part (hero section)
        Box(
            modifier = modifier
                .height(320.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .padding(start = 36.dp, top = 90.dp, end = 36.dp)
                    .fillMaxWidth()
            ){
                Image(
                    painter = painterResource(image),
                    contentDescription = "Image Item",
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .height(75.dp)
                )

                Text(
                    text = title,
                    modifier = modifier
                        .padding(top = 16.dp),
                    maxLines = 1,
                    fontFamily = jetFont,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = subtitle,
                        modifier = modifier
                            .width(200.dp)
                        ,
                        maxLines = 2,
                        fontFamily = jetFont,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Box {
                        FavoriteButton(
                            modifier = modifier
                                .testTag("favButton"),
                            isFavorite = isFav,
                            onClick = {
                                IDERepository.getInstance().updateFavIDE(ideID, !isFav)
                                isFav = !isFav
                            }
                        )
                    }
                }
            }

            IconButton(
                modifier = modifier.padding(16.dp),
                onClick = {
                    navigateBack()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Icon Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier.testTag("backButton")
                )
            }
        }

        // below part (description section)
        Box(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(MaterialTheme.colorScheme.outline)
        ){
            Column {
                Text(
                    text = description,
                    modifier = modifier
                        .padding(start = 36.dp, top = 36.dp, end = 36.dp),
                    fontFamily = jetFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.surface
                )

                FeatureButton(
                    text = "See All Feature",
                    onClick = {
                        startActivity(context, intentFeature, null)
                    },
                    modifier = modifier
                        .padding(horizontal = 36.dp, vertical = 100.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}