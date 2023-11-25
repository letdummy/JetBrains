package com.sekalisubmit.jetbrains.ui.component

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Slider(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 3000L,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int = images.size,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val context = LocalContext.current
    val internetState = remember { mutableStateOf(isInternetAvailable(context)) }

    LaunchedEffect(pagerState.currentPage) {
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
        isInternetAvailable(context)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .testTag("slider"),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            val imageUrl = if (internetState.value) images[page] else defaultImg[page]

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(200.dp),
                onError = {
                    defaultImg[page]
                }
            )
        }

        Surface(
            modifier = modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color(0xFF1F1F1F),
    unSelectedColor: Color = Color(0xFF989899),
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

// default data assigned to slider to make it ready to use
// note: make sure the resource have good view ratio
val images = listOf(
    "https://raw.githubusercontent.com/letdummy/dump/master/slider1.png",
    "https://raw.githubusercontent.com/letdummy/dump/master/slider2.png",
    "https://raw.githubusercontent.com/letdummy/dump/master/slider3.png",
)

val defaultImg = listOf(
    "android.resource://com.sekalisubmit.jetbrains/drawable/eslider1",
    "android.resource://com.sekalisubmit.jetbrains/drawable/eslider2",
    "android.resource://com.sekalisubmit.jetbrains/drawable/eslider3",
)

private fun isInternetAvailable(context: Context): Boolean {
    val result: Boolean
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}