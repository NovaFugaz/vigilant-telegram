package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
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
import com.bexai.bexaiinventory.domain.model.Producto

@Composable
fun StockItemCard(
    producto: Producto,
    onItemClick: () -> Unit = {}
) {
    // Esto va en lógica de negocio cuando ya la hagamos. So far, visual no más.
    val isQuiebre = producto.stockTienda == 0
    val isCritico = producto.stockTienda in 1..producto.stockMinimo

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onItemClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = producto.sku,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                // Badge dinámico para el estado (De momento) :C

                Surface(
                    color = when {

                        // Dios santo, los colores en HDR ni se ven ;;
                        isQuiebre -> Color(0xFFFEE2E2)
                        isCritico -> Color(0xFFFFEDD5)
                        else -> Color(0xFFDCFCE7)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = when {
                            isQuiebre -> "Quiebre"
                            isCritico -> "Stock Critico"
                            else -> "OK"
                        },
                        color = when {
                            isQuiebre -> Color(0xFF991B1B)
                            isCritico -> Color(0xFF9A3412)
                            else -> Color(0xFF166534)
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = producto.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = producto.descripcion, fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = Color.LightGray.copy(alpha = 0.4f)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
                    Text("Ubicación Rack", fontSize = 10.sp, color = Color.Gray)
                    Text("Pasillo ${producto.Ubicacion.pasillo}", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
                Column {
                    Text("Disponibilidad", fontSize = 10.sp, color = Color.Gray)
                    Text("${producto.stockTienda} / Mín. ${producto.stockMinimo} ${producto.unidadMedida.codigo}.", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
                Button(
                    onItemClick,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(if(isQuiebre) "Reposición" else "Ver Ficha", fontSize = 12.sp)
                }
            }
        }
    }
}