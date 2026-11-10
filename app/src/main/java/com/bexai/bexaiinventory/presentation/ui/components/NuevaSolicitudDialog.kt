package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bexai.bexaiinventory.domain.model.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaSolicitudDialog(
    productos: List<Producto>,
    productoInicial: Producto? = null,
    esAlertaManual: Boolean = false,
    onDismiss: () -> Unit,
    onConfirmarSolicitud: (sku: String, nombre: String, cantidad: Int, destino: String, prioridad: String, observacion: String) -> Unit,
    onConfirmarAlerta: (sku: String, nombre: String, stockActual: Int, stockRequerido: Int, ubicacion: String) -> Unit
) {
    var selectedProducto by remember { mutableStateOf(productoInicial ?: productos.firstOrNull()) }
    var cantidadText by remember { mutableStateOf("10") }
    var destino by remember { mutableStateOf("Bodega Central") }
    var prioridad by remember { mutableStateOf("Normal") }
    var observacion by remember { mutableStateOf("") }

    // Para alerta manual
    var stockActualText by remember { mutableStateOf(selectedProducto?.stockTienda?.toString() ?: "0") }
    var stockRequeridoText by remember { mutableStateOf(selectedProducto?.stockMinimo?.toString() ?: "10") }
    var ubicacionText by remember {
        mutableStateOf(
            selectedProducto?.let { "Pasillo ${it.Ubicacion.pasillo} - Zona ${it.Ubicacion.zona}" } ?: "Bodega Central"
        )
    }

    var expandedProductoDropdown by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = if (esAlertaManual) "Alerta de Quiebre" else "Solicitar Reposición",
                    style = MaterialTheme.typography.titleLarge
                )

                // Selector de Producto
                ExposedDropdownMenuBox(
                    expanded = expandedProductoDropdown,
                    onExpandedChange = { expandedProductoDropdown = !expandedProductoDropdown }
                ) {
                    OutlinedTextField(
                        value = selectedProducto?.let { "${it.sku} - ${it.nombre}" } ?: "Seleccionar producto",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Producto") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProductoDropdown) },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedProductoDropdown,
                        onDismissRequest = { expandedProductoDropdown = false }
                    ) {
                        productos.forEach { prod ->
                            DropdownMenuItem(
                                text = { Text("${prod.sku} - ${prod.nombre}") },
                                onClick = {
                                    selectedProducto = prod
                                    stockActualText = prod.stockTienda.toString()
                                    stockRequeridoText = prod.stockMinimo.toString()
                                    ubicacionText = "Pasillo ${prod.Ubicacion.pasillo} - Zona ${prod.Ubicacion.zona}"
                                    expandedProductoDropdown = false
                                }
                            )
                        }
                    }
                }

                if (!esAlertaManual) {
                    // Formulario Solicitud
                    OutlinedTextField(
                        value = cantidadText,
                        onValueChange = { cantidadText = it },
                        label = { Text("Cantidad requerida") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Destino
                    Text("Destino de Solicitud", style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Bodega Central", "Proveedor Directo").forEach { op ->
                            FilterChip(
                                selected = destino == op,
                                onClick = { destino = op },
                                label = { Text(op) }
                            )
                        }
                    }

                    // Prioridad
                    Text("Prioridad", style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Normal", "Urgente").forEach { prio ->
                            FilterChip(
                                selected = prioridad == prio,
                                onClick = { prioridad = prio },
                                label = { Text(prio) }
                            )
                        }
                    }

                    // Observación
                    OutlinedTextField(
                        value = observacion,
                        onValueChange = { observacion = it },
                        label = { Text("Observaciones / Notas") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                } else {
                    // Formulario Alerta Manual
                    OutlinedTextField(
                        value = stockActualText,
                        onValueChange = { stockActualText = it },
                        label = { Text("Stock Actual en Bodega") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = stockRequeridoText,
                        onValueChange = { stockRequeridoText = it },
                        label = { Text("Stock Mínimo Requerido") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = ubicacionText,
                        onValueChange = { ubicacionText = it },
                        label = { Text("Ubicación en Bodega") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val prod = selectedProducto ?: return@Button
                            if (!esAlertaManual) {
                                val cant = cantidadText.toIntOrNull() ?: 10
                                onConfirmarSolicitud(prod.sku, prod.nombre, cant, destino, prioridad, observacion)
                            } else {
                                val actual = stockActualText.toIntOrNull() ?: 0
                                val req = stockRequeridoText.toIntOrNull() ?: prod.stockMinimo
                                onConfirmarAlerta(prod.sku, prod.nombre, actual, req, ubicacionText)
                            }
                            onDismiss()
                        }
                    ) {
                        Text(if (esAlertaManual) "Emitir Alerta" else "Enviar Solicitud")
                    }
                }
            }
        }
    }
}
