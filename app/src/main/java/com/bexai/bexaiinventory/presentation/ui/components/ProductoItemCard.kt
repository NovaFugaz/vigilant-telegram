package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bexai.bexaiinventory.domain.model.Producto

@Composable
fun ProductoItemCard(
    producto: Producto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    seleccionado: Boolean = false,
    contado: Boolean = false
) {
    val colores = MaterialTheme.colorScheme

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colores.surface),
        border = if (seleccionado) BorderStroke(2.dp, colores.primary) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    producto.sku,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colores.onSurfaceVariant
                )
                if (contado) {
                    Text(
                        "✓ Contado",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colores.primary
                    )
                }
            }

            Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(producto.descripcion, fontSize = 12.sp, color = colores.onSurfaceVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Pasillo ${producto.Ubicacion.pasillo}", fontSize = 13.sp)
                Text(
                    "Stock: ${producto.stockTienda} ${producto.unidadMedida.codigo}.",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}