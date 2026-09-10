package com.example.metricdemo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Routes {
    const val SETUP = "setup"
    const val HOME = "home"
    const val ERRORS = "errors"
    const val TRACES = "traces"
    const val RELEASES = "releases"
    const val SOURCEMAPS = "sourcemaps"
    const val CONTEXT = "context"
    const val FEEDBACK = "feedback"
    const val ADVANCED = "advanced"
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SETUP) {
        composable(Routes.SETUP) {
            SetupScreen(onConnected = { navController.navigate(Routes.HOME) })
        }
        composable(Routes.HOME) {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) },
                onOpenSetup = { navController.navigate(Routes.SETUP) },
            )
        }
        composable(Routes.ERRORS) { ErrorsScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.TRACES) { TracesScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.RELEASES) { ReleasesScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.SOURCEMAPS) { SourceMapsScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.CONTEXT) { ContextScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.FEEDBACK) { FeedbackScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.ADVANCED) { AdvancedScreen(onBack = { navController.popBackStack() }) }
    }
}
