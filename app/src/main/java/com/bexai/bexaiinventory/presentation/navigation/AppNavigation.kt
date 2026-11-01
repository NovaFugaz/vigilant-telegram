package com.bexai.bexaiinventory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bexai.bexaiinventory.presentation.ui.view.LoginScreen
import com.bexai.bexaiinventory.presentation.ui.view.MainScreen
import com.bexai.bexaiinventory.presentation.ui.view.SeleccionSucursalScreen
import com.bexai.bexaiinventory.presentation.ui.view.splashScreen

object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val SELECCION = "seleccion/{codigo}"
    const val HOME = "home/{codigo}"
    fun seleccion(codigo: String) = "seleccion/$codigo"
    fun home(codigo: String) = "home/$codigo"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val argCodigo = listOf(navArgument("codigo") { type = NavType.StringType })

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {

        composable(Rutas.SPLASH) { splashScreen(navController) }

        composable(Rutas.LOGIN) {
            LoginScreen(
                logeado = { codigo ->
                    navController.navigate(Rutas.seleccion(codigo)) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.SELECCION, arguments = argCodigo) { entry ->
            val codigo = entry.arguments?.getString("codigo").orEmpty()
            SeleccionSucursalScreen(
                operadorCodigo = codigo,
                onJornadaIniciada = {
                    navController.navigate(Rutas.home(codigo)) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onCerrarSesion = {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.HOME, arguments = argCodigo) {
            MainScreen(
                onSinJornada = {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
    }
}