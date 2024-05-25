package com.fjr619.newsloc.presentation.navgraph

import android.app.Activity
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.fjr619.newsloc.presentation.bookmark.BookmarkScreen
import com.fjr619.newsloc.presentation.bookmark.BookmarkViewModel
import com.fjr619.newsloc.presentation.detail.DetailEvent
import com.fjr619.newsloc.presentation.detail.DetailScreen
import com.fjr619.newsloc.presentation.detail.DetailViewModel
import com.fjr619.newsloc.presentation.home.HomeScreen
import com.fjr619.newsloc.presentation.home.HomeViewModel
import com.fjr619.newsloc.presentation.search.SearchScreen
import com.fjr619.newsloc.presentation.search.SearchViewModel
import com.fjr619.newsloc.util.snackbar.SnackbarMessageHandler

val LocalAnimatedVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope> { error("") }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NewsGraph(
  newsNavController: NewsNavController,
) {

  val navController = newsNavController.navController
  val activity = LocalContext.current as? Activity

  SharedTransitionLayout {
    NavHost(
      navController = navController,
      startDestination = Route.NewsNavigation.route,
    ) {
      navigation(
        route = Route.NewsNavigation.route,
        startDestination = Route.HomeScreen.route
      ) {
        composable(route = Route.HomeScreen.route) { from ->
          val viewModel: HomeViewModel = hiltViewModel()
          val detailViewModel: DetailViewModel =
            from.hiltSharedViewModel(navController = navController)
          val items = viewModel.news.collectAsLazyPagingItems()

          CompositionLocalProvider(value = LocalAnimatedVisibilityScope provides this@composable) {
            HomeScreen(
              articles = items,
              navigateToSearch = newsNavController::navigateToBottomBarRoute,
              navigateToDetail = {
                detailViewModel.onEvent(DetailEvent.GetDetailArticle(it))
                detailViewModel.onEvent(DetailEvent.SetPrefixSharedKey("home"))
                newsNavController.navigateToDetail()
              },
              pullToRefreshLayoutState = viewModel.pullToRefreshState,
              onRefresh = {
                items.refresh()
              },
              onExitApp = {
                activity?.finish()
              }
            )
          }
        }

        composable(route = MaterialNavScreen.Search.route) { from ->
          val viewModel: SearchViewModel = hiltViewModel()
          val detailViewModel: DetailViewModel =
            from.hiltSharedViewModel(navController = navController)
          val state by viewModel.state

          CompositionLocalProvider(value = LocalAnimatedVisibilityScope provides this@composable) {
            SearchScreen(
              state = state,
              event = viewModel::onEvent,
              navigateToDetail = {
                detailViewModel.onEvent(DetailEvent.GetDetailArticle(it))
                detailViewModel.onEvent(DetailEvent.SetPrefixSharedKey("search"))
                newsNavController.navigateToDetail()
              }
            )
          }
        }

        composable(route = MaterialNavScreen.Bookmark.route) { from ->
          val viewModel: BookmarkViewModel = hiltViewModel()
          val detailViewModel: DetailViewModel =
            from.hiltSharedViewModel(navController = navController)
          val state by viewModel.state

          CompositionLocalProvider(value = LocalAnimatedVisibilityScope provides this@composable) {
            BookmarkScreen(
              state = state, navigateToDetails = {
                detailViewModel.onEvent(DetailEvent.GetDetailArticle(it))
                detailViewModel.onEvent(DetailEvent.SetPrefixSharedKey("bookmark"))
                newsNavController.navigateToDetail()
              }, onDelete = {
                detailViewModel.onEvent(DetailEvent.UpsertDeleteArticle(it))
              })
          }
        }

        composable(route = Route.DetailsScreen.route) { from ->
          val viewModel: DetailViewModel = from.hiltSharedViewModel(navController = navController)
          val viewState by viewModel.viewState.collectAsStateWithLifecycle()

          SnackbarMessageHandler(
            snackbarMessage = viewState.snackbarMessage,
            onDismissSnackbar = viewModel::dismissSnackbar
          )

          CompositionLocalProvider(value = LocalAnimatedVisibilityScope provides this@composable) {
            DetailScreen(
              prefixSharedKey = viewState.prefixSharedKey ?: "home",
              article = viewState.article,
              bookmarkArticle = viewState.bookmark,
              event = viewModel::onEvent,
              navigateUp = newsNavController::navigateBack,
            )
          }
        }
      }
    }
  }

}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.hiltSharedViewModel(
  navController: NavHostController,
): T {
  val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
  val parentEntry = remember(this) {
    navController.getBackStackEntry(navGraphRoute)
  }
  return hiltViewModel(parentEntry)
}
