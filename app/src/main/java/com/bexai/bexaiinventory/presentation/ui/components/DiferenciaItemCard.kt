package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.bexai.bexaiinventory.data.local.dao.DiferenciaStockInfo

@Composable
fun DiferenciaItemCard(
    diferenciaInfo: DiferenciaStockInfo
) {
    val esFaltante = diferenciaInfo.diferencia < 0

    val (colorMotivoFondo, colorMotivoTexto) = when (diferenciaInfo.motivo) {
        "Merma / Daño" -> Color(0xFFFEE2E2) to Color(0xFF991B1B)
        "Error previo" -> Color(0xFFFFEDD5) to Color(0xFF9A3412)
        "Traspaso no registrado" -> Color(0xFFF3E8FF) to Color(0xFF6B21A8)
        else -> Color(0xFFE2E8F0) to Color(0xFF334155)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header con SKU y Motivo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = diferenciaInfo.sku,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                Surface(
                    color = colorMotivoFondo,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = diferenciaInfo.motivo,
                        color = colorMotivoTexto,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = diferenciaInfo.nombreProducto, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = diferenciaInfo.descripcion, fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(10.dp))

            // Detalle Stock Teórico vs Conteo Físico vs Descalce
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Stock Teórico", fontSize = 10.sp, color = Color.Gray)
                    Text("${diferenciaInfo.stockTeorico} u.", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
                Column {
                    Text("Conteo Físico", fontSize = 10.sp, color = Color.Gray)
                    Text("${diferenciaInfo.conteoFisico} u.", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
                Column {
                    Text("Diferencia", fontSize = 10.sp, color = Color.Gray)
                    Surface(
                        color = if (esFaltante) Color(0xFFFEE2E2) else Color(0xFFDBEAFE),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = if (diferenciaInfo.diferencia > 0) "+${diferenciaInfo.diferencia} u." else "${diferenciaInfo.diferencia} u.",
                            color = if (esFaltante) Color(0xFFDC2626) else Color(0xFF1D4ED8),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Ubicación física exacta
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Ubicación: Pasillo ${diferenciaInfo.pasillo} • Est. ${diferenciaInfo.estante} • Niv. ${diferenciaInfo.nivel} • Zona ${diferenciaInfo.zona}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
