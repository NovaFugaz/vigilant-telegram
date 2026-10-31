package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime

@Composable
fun SyncStatusCard(
    ultimaSincronizacion: LocalDateTime?,
    estaSincronizando: Boolean,
    onSyncClick: () -> Unit = {}
) {
    val textoTiempo = ultimaSincronizacion?.formatearTiempoTranscurrido() ?: "OFF"

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Sincronización", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(
                    text = if (estaSincronizando) "Sincronizando..." else "Última vez: $textoTiempo",
                    fontSize = 11.sp,
                    color = if (estaSincronizando) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
            TextButton(
                onClick = onSyncClick,
                enabled = !estaSincronizando
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (estaSincronizando) "Procesando..." else "Actualizar")
            }
        }
    }
}