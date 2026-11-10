package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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

    var nombreError by remember { mutableStateOf<String?>(null) }
    var stockError by remember { mutableStateOf<String?>(null) }
    var pasilloError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar Producto", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Código QR / EAN: $codigoQr", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

                FormTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        if (it.isNotBlank()) nombreError = null
                    },
                    label = "Nombre del producto",
                    error = nombreError
                )
                FormTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = "Descripción"
                )
                FormTextField(
                    value = stockText,
                    onValueChange = {
                        stockText = it.filter { char -> char.isDigit() }
                        if (it.isNotBlank()) stockError = null
                    },
                    label = "Stock Inicial",
                    error = stockError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                FormTextField(
                    value = pasillo,
                    onValueChange = {
                        pasillo = it
                        if (it.isNotBlank()) pasilloError = null
                    },
                    label = "Ubicación Pasillo",
                    error = pasilloError
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val stock = stockText.toIntOrNull()
                    var esValido = true

                    if (nombre.isBlank()) {
                        nombreError = "El nombre del producto es obligatorio"
                        esValido = false
                    }
                    if (stock == null || stock < 0) {
                        stockError = "Ingrese un stock inicial mayor a 0"
                        esValido = false
                    }
                    if (pasillo.isBlank()) {
                        pasilloError = "La ubicación en pasillo es obligatoria"
                        esValido = false
                    }

                    if (esValido && stock != null) {
                        onGuardar(nombre.trim(), descripcion.trim(), stock, pasillo.trim())
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