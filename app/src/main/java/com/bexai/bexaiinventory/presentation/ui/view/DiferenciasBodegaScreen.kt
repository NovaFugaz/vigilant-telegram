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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
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
import com.bexai.bexaiinventory.presentation.ui.components.DiferenciaItemCard
import com.bexai.bexaiinventory.presentation.viewmodel.DiferenciasBodegaViewModel

@Composable
fun DiferenciasBodegaScreen(
    viewModel: DiferenciasBodegaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título / Resumen
        Text(
            text = "Control de Diferencias / Auditoría Física",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Comparativa stock en sistema vs. último conteo físico.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Buscador
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por SKU o producto...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = MaterialTheme.shapes.medium,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filtro por Motivo (Merma / Daño, Error previo, Traspaso no registrado)
        Text("Motivo del Descalce", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("Todos", "Merma / Daño", "Error previo", "Traspaso no registrado").forEach { motivo ->
                FilterChip(
                    selected = uiState.filtroMotivo == motivo,
                    onClick = { viewModel.onFiltroMotivoChange(motivo) },
                    label = { Text(motivo) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Filtro por Tipo (Faltantes / Sobrantes)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todos", "Faltantes (-)", "Sobrantes (+)").forEach { tipo ->
                FilterChip(
                    selected = uiState.filtroTipo == tipo,
                    onClick = { viewModel.onFiltroTipoChange(tipo) },
                    label = { Text(tipo) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Lista de Descalces
        if (uiState.cargando) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.descalcesFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se registraron diferencias de conteo físico para este filtro.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.descalcesFiltrados, key = { it.id }) { item ->
                    DiferenciaItemCard(diferenciaInfo = item)
                }
            }
        }
    }
}
