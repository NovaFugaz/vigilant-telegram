package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
import com.bexai.bexaiinventory.presentation.ui.navigation.AppNavHost
import com.bexai.bexaiinventory.presentation.ui.navigation.items
import com.bexai.bexaiinventory.presentation.viewmodel.JornadaEstado
import com.bexai.bexaiinventory.presentation.viewmodel.JornadaViewModel

@Composable
fun MainScreen(
    onSinJornada: () -> Unit,
    jornadaViewModel: JornadaViewModel = viewModel()
) {
    val estado by jornadaViewModel.estado.collectAsState()

    when (estado) {
        JornadaEstado.Cargando -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        // Sin jornada no se puede ver ni trabajar nada
        JornadaEstado.SinJornada -> LaunchedEffect(Unit) { onSinJornada() }

        is JornadaEstado.Iniciada -> {
            val navController = rememberNavController()
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            val titulo = items.find { it.route == currentRoute }?.title ?: "Inicio"

            Scaffold(
                topBar = { TopBar(subtitle = titulo) },
                bottomBar = {
                    NavigationBar {
                        items.forEach { screen ->
                            NavigationBarItem(
                                icon = { Icon(screen.icon, contentDescription = screen.title) },
                                label = { Text(screen.title) },
                                selected = currentRoute == screen.route,
                                onClick = {
                                    if (currentRoute != screen.route) {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}