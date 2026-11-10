package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
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
import com.bexai.bexaiinventory.domain.model.Producto
import com.bexai.bexaiinventory.presentation.ui.components.ProductoItemCard
import com.bexai.bexaiinventory.presentation.ui.components.RectificarConteoDialog
import com.bexai.bexaiinventory.presentation.ui.components.SeccionTitulo
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
import com.bexai.bexaiinventory.presentation.viewmodel.InventarioViewModel

@Composable
fun ConteoScreen(
    onVolver: () -> Unit,
    viewModel: InventarioViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var productoARectificar by remember { mutableStateOf<Producto?>(null) }

    val lista = state.filtrados
    val contadosEnLista = lista.count { it.sku in state.contados }
    val progreso = if (lista.isEmpty()) 0f else contadosEnLista.toFloat() / lista.size

    Column(Modifier.fillMaxSize()) {
        TopBar(subtitle = "Conteo y rectificación")

        Column(
            modifier = Modifier.weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // chips de pasillo
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.pasillos.forEach { pasillo ->
                    FilterChip(
                        selected = state.pasillo == pasillo,
                        onClick = { viewModel.onPasilloChange(pasillo) },
                        label = { Text(pasillo) }
                    )
                }
            }

            // progreso
            SeccionTitulo("Contados: $contadosEnLista / ${lista.size}")
            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(lista, key = { it.sku }) { producto ->
                    ProductoItemCard(
                        producto = producto,
                        contado = producto.sku in state.contados,
                        onClick = { productoARectificar = producto }
                    )
                }
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth().padding(16.dp).navigationBarsPadding()
        ) {
            Text("Volver al inicio")
        }
    }

    productoARectificar?.let { producto ->
        RectificarConteoDialog(
            producto = producto,
            onDismiss = { productoARectificar = null },
            onConfirmarRectificacion = { nuevoStock, _ ->
                viewModel.rectificar(producto.sku, nuevoStock)
                productoARectificar = null
            }
        )
    }
}