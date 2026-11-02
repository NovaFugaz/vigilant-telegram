package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
import com.bexai.bexaiinventory.presentation.ui.navigation.TabItem

@Composable
fun RoleScaffold(
    tabs: List<TabItem>, // lista de pestañas
    topBarActions: @Composable RowScope.() -> Unit, // para pasar botones o acciones al topbar
    info: String? = null
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val titulo = tabs.find { it.route == currentRoute }?.title ?: tabs.first().title

    Scaffold(
        // pasamos al tob pasando el subtitulo y la accion
        topBar = { TopBar(subtitle = titulo, actions = topBarActions) },
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title) },
                        selected = currentRoute == tab.route,
                        onClick = {
                            if (currentRoute != tab.route) {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    } // pero weon iba despues te odio kotlin
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
        Column(Modifier.padding(innerPadding)) {
            if (info != null) {
                Text(
                    text = info,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            NavHost(
                navController = navController,
                startDestination = tabs.first().route,
                modifier = Modifier.weight(1f)
            ) {
                tabs.forEach { tab -> // luego se cambia aqui el UnderConstruction .tab.algo()
                    composable(tab.route) { UnderConstructionScreen(title = tab.title) }
                }
            }
        }
    }
}