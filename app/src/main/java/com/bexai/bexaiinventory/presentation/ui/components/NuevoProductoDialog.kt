package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NuevoProductoDialog(
    codigoQr: String,
    onDismiss: () -> Unit,
    onGuardar: (nombre: String, descripcion: String, stock: Int, pasillo: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("10") }
    var pasillo by remember { mutableStateOf("A-01") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar Producto por QR", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Código QR / EAN: $codigoQr", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

                FormTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Nombre del producto"
                )
                FormTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = "Descripción"
                )
                FormTextField(
                    value = stockText,
                    onValueChange = { stockText = it },
                    label = "Stock Inicial"
                )
                FormTextField(
                    value = pasillo,
                    onValueChange = { pasillo = it },
                    label = "Ubicación Pasillo"
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nombre.isNotBlank()) {
                        val stock = stockText.toIntOrNull() ?: 0
                        onGuardar(nombre, descripcion, stock, pasillo)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}