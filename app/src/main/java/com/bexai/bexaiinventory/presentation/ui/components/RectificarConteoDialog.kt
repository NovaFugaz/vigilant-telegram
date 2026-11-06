package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.bexai.bexaiinventory.domain.model.Producto

@Composable
fun RectificarConteoDialog(
    producto: Producto,
    onDismiss: () -> Unit,
    onConfirmarRectificacion: (nuevoStock: Int, motivo: String) -> Unit
) {
    var stockContado by remember { mutableIntStateOf(producto.stockTienda) }
    var motivoSeleccionado by remember { mutableStateOf("Merma / Daño") }

    val diferencia = stockContado - producto.stockTienda

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Rectificación de Stock", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(producto.nombre, fontSize = 13.sp, color = Color.Gray)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Stock Teórico vs Físico
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Stock Sistema", fontSize = 11.sp, color = Color.Gray)
                        Text("${producto.stockTienda} u.", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Físico Contado", fontSize = 11.sp, color = Color.Gray)
                        Text("$stockContado u.", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }

                // Banner de Descalce
                Surface(
                    color = if (diferencia == 0) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = when {
                            diferencia == 0 -> "OK"
                            diferencia < 0 -> "$diferencia u. (FALTA)"
                            else -> "+$diferencia u. (SOBRA)"
                        },
                        color = if (diferencia == 0) Color(0xFF166534) else Color(0xFF991B1B),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Contadores y Botones +1, +5, -1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = { if (stockContado > 0) stockContado-- }) { Text("-1") }
                    OutlinedButton(onClick = { stockContado += 1 }) { Text("+1") }
                    OutlinedButton(onClick = { stockContado += 5 }) { Text("+5") }
                }

                // Selector de Motivo del Descalce
                Text("Motivo del Descalce:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                listOf("Merma / Daño", "Error previo", "Traspaso no reg.").forEach { motivo ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = motivoSeleccionado == motivo,
                            onClick = { motivoSeleccionado = motivo }
                        )
                        Text(motivo, fontSize = 13.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirmarRectificacion(stockContado, motivoSeleccionado) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Confirmar Rectificación")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}