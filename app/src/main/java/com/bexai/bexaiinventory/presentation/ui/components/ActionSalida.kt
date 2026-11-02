package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun ActionSalida(
    descripcion: String, // para el contentDescription
    titulo: String,
    mensaje: String,
    textoConfirmar: String,
    onConfirmar: () -> Unit
){
    // se inicia en false
    var mostrar by remember { mutableStateOf(false) }

    IconButton(onClick = {mostrar = true}) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Logout,
            contentDescription = descripcion,
            tint = Color.White
        )
    }
    if (mostrar) {
        AlertDialog(
            onDismissRequest = { mostrar = false },
            title = { Text(titulo) },
            text = { Text(mensaje) },
            confirmButton = {
                TextButton(onClick = { mostrar = false; onConfirmar() }) {
                    Text(textoConfirmar)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrar = false} ) { Text("Cancelar") }
            }
        )
    }
}