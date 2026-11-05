package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.ui.components.BranchSelectorCard
import com.bexai.bexaiinventory.presentation.ui.components.StockItemCard
import com.bexai.bexaiinventory.presentation.ui.components.SyncStatusCard
import com.bexai.bexaiinventory.presentation.viewmodel.StockViewModel

@Composable
fun StockCatalogScreen(
    onAbrirEscaner: () -> Unit = {},
    stockViewModel: StockViewModel = viewModel()
) {
    // Escuchamos el estado reactivo desde el ViewModel (conectado a Room)
    val uiState by stockViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1. Selector de Sucursal
        BranchSelectorCard(sucursalNombre = "Bodega Central Pudahuel")

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Barra de Búsqueda
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { stockViewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar SKU, EAN o descripción") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = onAbrirEscaner) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null)
                }
            },
            shape = MaterialTheme.shapes.medium,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Chips de Filtro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todos", "Crítico", "Quiebre").forEach { filtro ->
                FilterChip(
                    selected = uiState.selectedFilter == filtro,
                    onClick = { stockViewModel.onFilterChange(filtro) },
                    label = { Text(filtro) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Lista de Productos o Carga
        if (uiState.cargando) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.productosFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontraron productos en el inventario.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.productosFiltrados, key = { it.sku }) { producto ->
                    StockItemCard(producto = producto) {
                        // Acción al presionar el producto o el botón de la tarjeta
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 5. Estado de Sincronización
        SyncStatusCard(
            ultimaSincronizacion = uiState.syncInfo.ultimaSincronizacion,
            estaSincronizando = uiState.syncInfo.isSincronizando
        ) {
            stockViewModel.actualizarSincronizacion()
        }
    }
}