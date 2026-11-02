package com.bexai.bexaiinventory.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

val tabsBodega = listOf(
    TabItem("bodega_stock", "Stock", Icons.Default.Inventory2),
    TabItem("bodega_diferencias", "Diferencias", Icons.Default.FactCheck),
    TabItem("bodega_reposicion", "Reposición", Icons.Default.AddAlert)
)

val tabsGerencia = listOf(
    TabItem("gerencia_tablero", "Tablero", Icons.Default.Dashboard),
    TabItem("gerencia_tiendas", "Tiendas", Icons.Default.Store),
    TabItem("gerencia_auditoria", "Auditoría", Icons.AutoMirrored.Filled.ReceiptLong),
    TabItem("gerencia_alertas", "Alertas", Icons.Default.Notifications),
    TabItem("gerencia_reportes", "Reportes", Icons.Default.Assessment)
)