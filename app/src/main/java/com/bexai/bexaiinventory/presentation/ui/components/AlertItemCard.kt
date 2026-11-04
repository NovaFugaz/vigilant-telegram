package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bexai.bexaiinventory.domain.model.Alerta
import com.bexai.bexaiinventory.domain.model.SeveridadAlerta

@Composable
fun AlertItemCard(
    alerta: Alerta,
    onAccionClick: (Alerta) -> Unit = {}
){
    val (colorFondo, colorTexto, textoAccion) = when (alerta.severidad) {
        SeveridadAlerta.QUIEBRE_CRITICO -> Triple(
            Color(0xFFFEE2E2), Color(0xFF991B1B), "GENERAR ORDEN TRASPASO"
        )
        // Se me quedó un ) y me estaba rompiendo la cabeza de dónde era el error >.<
        SeveridadAlerta.STOCK_CRITICO_INMINENTE -> Triple(
            Color(0xFFFFEDD5), Color(0xFF9A3412), "APROBAR REPOSICIÓN INMEDIATA"
        )
        SeveridadAlerta.DESCALCE_CONTEO -> Triple(
            Color(0XFFFEF3C7), Color(0XFF92400E), "REVISAR DESCALCE EN BODEGA"
        )
        SeveridadAlerta.ADVERTENCIA_PREVENTIVA -> Triple(
            Color(0xFFE0F2FE), Color(0XFF075985), "PROGRAMAR PEDIDO REGULAR"
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = colorFondo,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = alerta.tituloAlerta,
                        color = colorTexto,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = "${alerta.hora.hour}: ${"0%2d".format(alerta.hora.minute)} UTC-3",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ubicación
            Text(
                text = alerta.ubicacionNombre,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Indicadores de stock actual vs requerido

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    Text("Stock Actual", fontSize = 10.sp, color = Color.Gray)
                    Text(
                        text = "${alerta.stockActual} u.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (alerta.stockActual == 0) Color.Red else Color.Unspecified
                    )
                }
                Column {
                    Text("Stock Minimo Requerido", fontSize = 10.sp, color = Color.Gray)
                    Text(
                        text = "${alerta.stockRequerido} u.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón de Acción Principal

            Button(
                onClick = {onAccionClick(alerta)},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorTexto),
                enabled = !alerta.atendida
            ) {
                Text(
                    text = if (alerta.atendida) "ATENDIDA / EN PROCESO" else textoAccion,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}