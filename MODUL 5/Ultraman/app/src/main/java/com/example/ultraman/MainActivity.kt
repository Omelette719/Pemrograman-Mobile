package com.example.ultraman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ultraman.ui.ViewModel.UltramanViewModel
import com.example.ultraman.ui.ViewModel.UltramanViewModelFactory
import com.example.ultraman.ui.components.BottomBarNavigation
import com.example.ultraman.ui.screens.DetailScreen
import com.example.ultraman.ui.screens.FavoriteScreen
import com.example.ultraman.ui.screens.ListScreen
import com.example.ultraman.ui.theme.UltramanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UltramanTheme {
                val navController = rememberNavController()
                val viewModel: UltramanViewModel = viewModel(
                    factory = UltramanViewModelFactory(applicationContext)
                )
                UltramanApp(navController, viewModel)
            }
        }
    }
}

@Composable
fun UltramanApp(
    navController: NavHostController,
    viewModel: UltramanViewModel
) {
    Scaffold(
        bottomBar = {
            BottomBarNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("list") {
                ListScreen(navController, viewModel)
            }
            composable("favorite") {
                FavoriteScreen(navController, viewModel)
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
}
