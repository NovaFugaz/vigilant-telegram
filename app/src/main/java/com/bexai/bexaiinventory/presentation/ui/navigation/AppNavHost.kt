package com.bexai.bexaiinventory.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bexai.bexaiinventory.presentation.ui.view.HomeOperadorScreen
import com.bexai.bexaiinventory.presentation.ui.view.StockCatalogScreen
import com.bexai.bexaiinventory.presentation.ui.view.UnderConstructionScreen


@Composable
fun AppNavHost (
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route,
        modifier = modifier
    ){
      composable(Screen.Inicio.route){
          HomeOperadorScreen()
      }
      composable(Screen.Stock.route) {
          StockCatalogScreen()
      }
      composable(Screen.Alertas.route) {
          UnderConstructionScreen(title = "Alertas")
      }
      composable(Screen.Auditoria.route) {
          UnderConstructionScreen(title = "Auditoria")
      }
    }
}