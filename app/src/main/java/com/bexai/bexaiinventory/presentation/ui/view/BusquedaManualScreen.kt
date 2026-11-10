package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.ui.components.FormTextField
import com.bexai.bexaiinventory.presentation.ui.components.PrimaryButton
import com.bexai.bexaiinventory.presentation.ui.components.ProductoItemCard
import com.bexai.bexaiinventory.presentation.ui.components.RectificarConteoDialog
import com.bexai.bexaiinventory.presentation.ui.components.SeccionTitulo
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
import com.bexai.bexaiinventory.presentation.viewmodel.InventarioViewModel

@Composable
fun BusquedaManualScreen(
    onVolver: () -> Unit,
    viewModel: InventarioViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }
    val seleccionado = state.seleccionado

    Column(Modifier.fillMaxSize()) {
        TopBar(subtitle = "Búsqueda manual")

        Column(
            modifier = Modifier.weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FormTextField(
                value = state.query,
                onValueChange = viewModel::onQueryChange,
                label = "SKU, EAN o nombre"
            )

            SeccionTitulo("${state.filtrados.size} coincidencias")

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.filtrados, key = { it.sku }) { producto ->
                    ProductoItemCard(
                        producto = producto,
                        seleccionado = producto.sku == state.seleccionadoSku,
                        contado = producto.sku in state.contados,
                        onClick = { viewModel.onSeleccionar(producto.sku) }
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(16.dp).navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrimaryButton(
                text = "Continuar",
                onClick = { mostrarDialogo = true },
                enabled = seleccionado != null,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Volver al inicio")
            }
        }
    }

    if (mostrarDialogo && seleccionado != null) {
        RectificarConteoDialog(
            producto = seleccionado,
            onDismiss = { mostrarDialogo = false },
            onConfirmarRectificacion = { nuevoStock, _ ->
                viewModel.rectificar(seleccionado.sku, nuevoStock)
                mostrarDialogo = false
            }
        )
    }
}