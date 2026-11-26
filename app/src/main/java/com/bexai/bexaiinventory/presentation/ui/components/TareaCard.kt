package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bexai.bexaiinventory.domain.model.AccionTarea
import com.bexai.bexaiinventory.domain.model.Tarea

@Composable
fun TareaCard(
    tarea: Tarea,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val urgente = tarea.accion == AccionTarea.REVISAR
    val colores = MaterialTheme.colorScheme

    val transicion = rememberInfiniteTransition(label = "pulso")
    val alfaBorde by transicion.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "alfaBorde"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (urgente) colores.errorContainer else colores.surface
        ),
        border = if (urgente) BorderStroke(2.dp, colores.error.copy(alpha = alfaBorde)) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    tarea.titulo,
                    fontWeight = FontWeight.Bold,
                    color = if (urgente) colores.onErrorContainer else colores.onSurface
                )
                Text(
                    tarea.detalle,
                    fontSize = 12.sp,
                    color = if (urgente) colores.onErrorContainer else colores.onSurface
                )
            }
            PrimaryButton(
                text = tarea.accion.titulo,
                onClick = onClick,
                modifier = Modifier.width(130.dp),
                containerColor = if (urgente) colores.error else colores.primary
            )
        }
    }
}