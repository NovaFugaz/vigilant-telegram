package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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

    var productoError by remember { mutableStateOf<String?>(null) }
    var cantidadError by remember { mutableStateOf<String?>(null) }
    var stockActualError by remember { mutableStateOf<String?>(null) }
    var stockRequeridoError by remember { mutableStateOf<String?>(null) }
    var ubicacionError by remember { mutableStateOf<String?>(null) }

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
                        isError = productoError != null,
                        supportingText = {
                            if (productoError != null) {
                                Text(productoError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
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
                                    productoError = null
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
                        onValueChange = { input ->
                            cantidadText = input.filter { char -> char.isDigit() }
                            if (cantidadText.isNotBlank()) cantidadError = null
                        },
                        label = { Text("Cantidad requerida") },
                        isError = cantidadError != null,
                        supportingText = {
                            if (cantidadError != null) {
                                Text(cantidadError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        onValueChange = { input ->
                            stockActualText = input.filter { char -> char.isDigit() }
                            if (stockActualText.isNotBlank()) stockActualError = null
                        },
                        label = { Text("Stock Actual en Bodega") },
                        isError = stockActualError != null,
                        supportingText = {
                            if (stockActualError != null) {
                                Text(stockActualError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = stockRequeridoText,
                        onValueChange = { input ->
                            stockRequeridoText = input.filter { char -> char.isDigit() }
                            if (stockRequeridoText.isNotBlank()) stockRequeridoError = null
                        },
                        label = { Text("Stock Mínimo Requerido") },
                        isError = stockRequeridoError != null,
                        supportingText = {
                            if (stockRequeridoError != null) {
                                Text(stockRequeridoError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = ubicacionText,
                        onValueChange = {
                            ubicacionText = it
                            if (it.isNotBlank()) ubicacionError = null
                        },
                        label = { Text("Ubicación en Bodega") },
                        isError = ubicacionError != null,
                        supportingText = {
                            if (ubicacionError != null) {
                                Text(ubicacionError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
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
                            val prod = selectedProducto
                            if (prod == null) {
                                productoError = "Debe seleccionar un producto"
                                return@Button
                            }

                            if (!esAlertaManual) {
                                val cant = cantidadText.toIntOrNull()
                                if (cant == null || cant <= 0) {
                                    cantidadError = "Ingrese una cantidad válida mayor a 0"
                                } else {
                                    onConfirmarSolicitud(prod.sku, prod.nombre, cant, destino, prioridad, observacion)
                                    onDismiss()
                                }
                            } else {
                                val actual = stockActualText.toIntOrNull()
                                val req = stockRequeridoText.toIntOrNull()
                                var esValido = true

                                if (actual == null || actual < 0) {
                                    stockActualError = "Ingrese un valor de stock válido mayor a 0"
                                    esValido = false
                                }
                                if (req == null || req <= 0) {
                                    stockRequeridoError = "Ingrese un stock mínimo requerido mayor a 0"
                                    esValido = false
                                }
                                if (ubicacionText.isBlank()) {
                                    ubicacionError = "La ubicación no puede estar vacía"
                                    esValido = false
                                }

                                if (esValido && actual != null && req != null) {
                                    onConfirmarAlerta(prod.sku, prod.nombre, actual, req, ubicacionText.trim())
                                    onDismiss()
                                }
                            }
                        }
                    ) {
                        Text(if (esAlertaManual) "Emitir Alerta" else "Enviar Solicitud")
                    }
                }
            }
        }
    }
}
