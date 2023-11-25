package com.sekalisubmit.jetbrains

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.sekalisubmit.jetbrains.ui.navigation.Screen
import com.sekalisubmit.jetbrains.ui.theme.JetBrainsTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
class JetBrainsAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetBrainsTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetBrainsApp(navController = navController)
            }
        }
    }

    @Test // start destination test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test // navigation test
    fun navHost_navigationTest() {
        navigateToAndAssert(Screen.Favorite.route, "favoriteScreen")
        navigateToAndAssert(Screen.Profile.route, "profileScreen")
        navigateToAndAssert(Screen.Setting.route, "settingScreen")
        navigateToAndAssert(Screen.Home.route, "homeScreen")
        navigateToAndAssert(Screen.Profile.route, "profileScreen")
        navigateToAndAssert(Screen.Home.route, "homeScreen")
    }

    @Test // positive search case
    fun search_query_correct() {
        performSearch("Android", "searchSuccess")
    }

    @Test // negative search case
    fun search_query_incorrect() {
        performSearch("Jaka Jack", "searchFail")
    }

    @Test // full app flow in one test
    fun total_test() {
        clickOnCategories("communityCategory", "googleCategory", "non-communityCategory", "allCategory")

        performScrollAndCheck("IDE_13")
        performScrollAndCheck("IDE_1")

        performClickAndAssert("IDE_7", "detailScreen")
        performClickAndAssert("backButton", "homeScreen")

        navigateToAndAssert(Screen.Favorite.route, "favoriteScreen")
        assertNodesWithTags("favUnavailable")

        navigateToAndAssert(Screen.Home.route, "homeScreen")
        performClickAndAssert("IDE_7", "detailScreen")
        performClickAndAssert("favButton", "detailScreen")
        performClickAndAssert("backButton", "homeScreen")

        navigateToAndAssert(Screen.Favorite.route, "favoriteScreen")
        assertNodesWithTags("favAvailable")

        performClickAndAssert("IDE_7", "detailScreen")
        performClickAndAssert("favButton", "detailScreen")
        performClickAndAssert("backButton", "favoriteScreen")
        assertNodesWithTags("favUnavailable")

        navigateToAndAssert(Screen.Profile.route, "profileScreen")
        assertNodesWithTags("email", "github", "linkedin")

        navigateToAndAssert(Screen.Setting.route, "settingScreen")
        assertNodesWithTags("themeSetting", "contributeSetting")
    }

    private fun navigateToAndAssert(route: String, tag: String) {
        composeTestRule.onNodeWithTag(route, useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
    }

    private fun performSearch(query: String, resultTag: String) {
        composeTestRule.onNodeWithText("Find your preferred IDE").performTextInput(query)
        composeTestRule.onNodeWithTag(resultTag).assertExists()
    }

    private fun clickOnCategories(vararg tags: String) {
        for (tag in tags) {
            composeTestRule.onNodeWithTag(tag, useUnmergedTree = true).performClick()
        }
    }

    private fun performScrollAndCheck(ideTag: String) {
        composeTestRule.onNodeWithTag("homeScreen").performScrollToNode(hasTestTag(ideTag))
    }

    private fun performClickAndAssert(tag: String, route: String) {
        composeTestRule.onNodeWithTag(tag, useUnmergedTree = true).performClick()
        assertNodesWithTags(route)
    }

    private fun assertNodesWithTags(vararg tags: String) {
        for (tag in tags) {
            composeTestRule.onNodeWithTag(tag, useUnmergedTree = true).assertIsDisplayed()
        }
    }
}
