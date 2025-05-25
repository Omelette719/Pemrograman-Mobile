package com.example.ultraman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.ultraman.ui.ViewModel.UltramanViewModel
import com.example.ultraman.ui.ViewModel.UltramanViewModelFactory
import com.example.ultraman.ui.screens.DetailScreen
import com.example.ultraman.ui.screens.ListScreen
import com.example.ultraman.ui.theme.UltramanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UltramanTheme {
                val navController = rememberNavController()
                val viewModel: UltramanViewModel = viewModel(factory = UltramanViewModelFactory())
                AppNavHost(navController, viewModel)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, viewModel: UltramanViewModel) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(navController, viewModel)
        }
        composable(
            "detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            DetailScreen(itemId, viewModel)
        }
    }
}