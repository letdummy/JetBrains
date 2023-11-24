package com.sekalisubmit.jetbrains.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sekalisubmit.jetbrains.di.Injection
import com.sekalisubmit.jetbrains.model.FakeIDEData
import com.sekalisubmit.jetbrains.model.FavsIDE
import com.sekalisubmit.jetbrains.ui.ViewModelFactory
import com.sekalisubmit.jetbrains.ui.common.UIState
import com.sekalisubmit.jetbrains.ui.component.ItemLayout
import com.sekalisubmit.jetbrains.ui.component.JetSearchBar
import com.sekalisubmit.jetbrains.ui.component.Slider

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }

    viewModel.uiState.collectAsState(initial = UIState.Loading).value.let { uiState ->
        when (uiState) {
            is UIState.Loading -> {
                viewModel.getAllIDE()
            }

            is UIState.Success -> {
                HomeContent(
                    favsIDE = uiState.data,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { newQuery ->
                        searchQuery = newQuery
                        viewModel.searchIDE(newQuery)
                    },
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    navController = navController
                )
            }

            is UIState.Error -> {}
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun HomeContent(
    favsIDE: List<FavsIDE>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    navController: NavController
){
    val categories = FakeIDEData.dummyIDE.map { it.category }.distinct()
    var selectedCategories by remember { mutableStateOf<Set<String>>(emptySet()) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val filteredItems = if (selectedCategories.isNotEmpty()) {
            favsIDE.filter { it.ide.category in selectedCategories }
        } else {
            favsIDE
        }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {},
                scrollBehavior = scrollBehavior,
                actions = {
                    JetSearchBar(
                        query = searchQuery,
                        onQueryChange = onSearchQueryChanged,
                        navController = navController
                    )
                },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .testTag("Home_TopBar")
            )
        }
    ){ innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .testTag("Home_Screen")
        ) {
            item {
                Card(
                    modifier = Modifier.padding(4.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Slider()
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    // All Button
                    Button(
                        onClick = {
                            selectedCategories = emptySet()
                        },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategories.isEmpty()) Color.LightGray else Color(0xFF121314)
                        ),
                    ) {
                        Text(
                            text = "All",
                            color = if (selectedCategories.isEmpty()) Color.Black else Color.White
                            )
                    }

                    // Each IDE categories button
                    categories.forEach { category ->
                        val isFilled = category in selectedCategories

                        Button(
                            onClick = {
                                // toggle the category that will back to all if the category is re-selected
                                selectedCategories = if (isFilled) {
                                    selectedCategories = emptySet()
                                    selectedCategories - category
                                } else {
                                    selectedCategories = emptySet()
                                    selectedCategories + category
                                }
                            },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFilled) Color.LightGray else Color(0xFF121314)
                            )
                        ) {
                            Text(
                                text = category,
                                color = if (isFilled) Color.Black else Color.White
                            )
                        }
                    }
                }
            }

            item{
                for (item in filteredItems) {
                    ItemLayout(
                        image = item.ide.image,
                        title = item.ide.title,
                        subtitle = item.ide.subtitle,
                        modifier = modifier
                            .clickable {
                                navigateToDetail(item.ide.id)
                            }
                    )
                }
            }
        }
    }
}