package com.bexai.bexaiinventory.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bexai.bexaiinventory.presentation.ui.view.HomeOperadorScreen
import com.bexai.bexaiinventory.presentation.ui.view.AlertasScreen
import com.bexai.bexaiinventory.presentation.ui.view.StockCatalogScreen
import com.bexai.bexaiinventory.presentation.ui.view.UnderConstructionScreen


@Composable
fun AppNavHost (
    navController: NavHostController,
    operadorCodigo: String,
    onAbrirEscaner: () -> Unit = {},
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route,
        modifier = modifier
    ){
      composable(Screen.Inicio.route){
          HomeOperadorScreen(operadorCodigo = operadorCodigo,
              onAbrirScanner = onAbrirEscaner)
      }
      composable(Screen.Stock.route) {
          StockCatalogScreen(onAbrirEscaner = onAbrirEscaner)
      }
      composable(Screen.Alertas.route) {
          AlertasScreen()
      }
      composable(Screen.Auditoria.route) {
          UnderConstructionScreen(title = "Auditoria")
      }
    }
}