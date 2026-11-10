package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.ui.components.BranchSelectorCard
import com.bexai.bexaiinventory.presentation.ui.components.StockItemCard
import com.bexai.bexaiinventory.presentation.viewmodel.BodegaStockViewModel

@Composable
fun BodegaStockScreen(
    onAbrirEscaner: () -> Unit = {},
    viewModel: BodegaStockViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Selector de Sucursal
        BranchSelectorCard(sucursalNombre = "Bodega Principal - Sucursal 1")

        Spacer(modifier = Modifier.height(12.dp))

        // Buscador por SKU / Descripción
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por SKU o descripción...") },
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

        // Chips de Filtro por Estado de Stock (Normal, Crítico, Quiebre)
        Text("Estado de Stock", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todos", "Normal", "Crítico", "Quiebre").forEach { estado ->
                FilterChip(
                    selected = uiState.selectedEstado == estado,
                    onClick = { viewModel.onEstadoChange(estado) },
                    label = { Text(estado) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Chips de Filtro por Categoría
        if (uiState.categoriasDisponibles.size > 1) {
            Text("Categoría", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.categoriasDisponibles.take(4).forEach { cat ->
                    FilterChip(
                        selected = uiState.selectedCategoria == cat,
                        onClick = { viewModel.onCategoriaChange(cat) },
                        label = { Text(cat) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Lista de Productos
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
                Text("No se encontraron productos con los filtros seleccionados.")
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
                        // Acción al presionar tarjeta de producto
                    }
                }
            }
        }
    }
}
