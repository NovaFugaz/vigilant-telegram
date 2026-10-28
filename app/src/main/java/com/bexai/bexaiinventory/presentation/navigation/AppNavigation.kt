package com.bexai.bexaiinventory.presentation.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bexai.bexaiinventory.presentation.ui.view.LoginScreen
import com.bexai.bexaiinventory.presentation.ui.view.MainScreen
import com.bexai.bexaiinventory.presentation.ui.view.splashScreen

object Rutas {
    const val LOGIN = "login"
    const val HOME = "home"
    const val SPLASH = "splash"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {

        composable(Rutas.SPLASH) {
            splashScreen(navController)
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                logeado = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.HOME) {
            MainScreen()
        }
    }
}