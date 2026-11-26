package com.bexai.bexaiinventory.presentation.ui.components

import android.util.MutableInt
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    compact: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {

    val interactionSource = remember { MutableInteractionSource() }
    val presionado by interactionSource.collectIsPressedAsState()

    val escala by animateFloatAsState(
        targetValue = if (presionado) 0.94f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "escalaBoton"
    )
    val colorAnimado by animateColorAsState(
        targetValue = if (presionado) lerp(containerColor, Color.Black, 0.25f) else containerColor,
        label = "colorBoton"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = modifier.graphicsLayer {
            scaleX = escala
            scaleY = escala
        },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorAnimado),
        contentPadding = if (compact) PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        else ButtonDefaults.ContentPadding
    ) {
        Text(
            text = text,
            fontSize = if (compact) 12.sp else 18.sp,
            fontWeight = if (compact) FontWeight.Bold else null
        )
    }
}