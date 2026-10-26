package com.bexai.bexaiinventory.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Inicio: Screen("inicio", "Inicio", Icons.Default.Home )
    object Stock: Screen("stock", "Stock",Icons.Default.Inventory2)
    object Alertas: Screen("alertas", "Alertas", Icons.Default.Notifications)
    object Auditoria: Screen("auditoria", "Auditoria", Icons.AutoMirrored.Filled.ReceiptLong)
}

val items = listOf(
    Screen.Inicio,
    Screen.Stock,
    Screen.Alertas,
    Screen.Auditoria
)