package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bexai.bexaiinventory.presentation.ui.components.PrimaryButton

@Composable
fun HomeOperadorScreen(
    onAbrirScanner: () -> Unit = {} // por ahora vacio
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        //  MÉTRICAS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Métricas de la jornada", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            // aquí irán las métricas
        }

        //  SCANNER
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Acceso al scanner", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            PrimaryButton(
                text = "Abrir Escáner",
                onClick = onAbrirScanner
            )
        }

        //  TAREAS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Tareas asignadas del turno", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            // aquí irán las tareas
        }
    }
}