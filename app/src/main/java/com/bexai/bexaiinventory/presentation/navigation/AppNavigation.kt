package com.bexai.bexaiinventory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bexai.bexaiinventory.domain.model.Rol
import com.bexai.bexaiinventory.presentation.ui.view.BodegaMainScreen
import com.bexai.bexaiinventory.presentation.ui.view.GerenciaMainScreen
import com.bexai.bexaiinventory.presentation.ui.view.LoginScreen
import com.bexai.bexaiinventory.presentation.ui.view.MainScreen
import com.bexai.bexaiinventory.presentation.ui.view.SeleccionSucursalScreen
import com.bexai.bexaiinventory.presentation.ui.view.splashScreen

object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val SELECCION = "seleccion/{codigo}"
    const val HOME_TERRENO = "home_terreno/{codigo}"
    const val HOME_BODEGA = "home_bodega/{codigo}"
    const val HOME_GERENCIA = "home_gerencia/{codigo}"

    fun seleccion(codigo: String) = "seleccion/$codigo"

    // Gerencia no pasa por jornada
    fun requiereJornada(rol: Rol) = rol != Rol.GERENCIA

    // Cada rol tiene su pantalla principal
    // en el caso de gerencia va al tablero, bodega y terreno pensare en algo
    fun inicioPara(rol: Rol, codigo: String) = when (rol) {
        Rol.TERRENO -> "home_terreno/$codigo"
        Rol.BODEGA -> "home_bodega/$codigo"
        Rol.GERENCIA -> "home_gerencia/$codigo"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val argCodigo = listOf(navArgument("codigo") { type = NavType.StringType })

    fun cerrarSesion(nav: NavController) {
        nav.navigate(Rutas.LOGIN) {
            popUpTo(nav.graph.id) { inclusive = true }
        }
    }

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {

        composable(Rutas.SPLASH) { splashScreen(navController) }

        composable(Rutas.LOGIN) {
            LoginScreen(
                logeado = { codigo, rol ->
                    val destino = if (Rutas.requiereJornada(rol)) {
                        Rutas.seleccion(codigo)
                    } else {
                        Rutas.inicioPara(rol, codigo)
                    }
                    navController.navigate(destino) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.SELECCION, arguments = argCodigo) { entry ->
            val codigo = entry.arguments?.getString("codigo").orEmpty()
            SeleccionSucursalScreen(
                operadorCodigo = codigo,
                onJornadaIniciada = { rol ->
                    navController.navigate(Rutas.inicioPara(rol, codigo)) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onCerrarSesion = { cerrarSesion(navController) }
            )
        }

        // Operador (el que tiene cámara)
        composable(Rutas.HOME_TERRENO, arguments = argCodigo) {
            MainScreen(onSinJornada = { cerrarSesion(navController) })
        }

        // Encargado de bodega
        composable(Rutas.HOME_BODEGA, arguments = argCodigo) {
            BodegaMainScreen(
                onSinJornada = { cerrarSesion(navController) },
            )
        }

        // Gerencia (sin jornada)
        composable(Rutas.HOME_GERENCIA, arguments = argCodigo) {
            GerenciaMainScreen(onCerrarSesion = { cerrarSesion(navController) })
        }
    }
}