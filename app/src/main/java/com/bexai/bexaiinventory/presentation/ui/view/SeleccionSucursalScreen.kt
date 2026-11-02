package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.ui.components.PrimaryButton
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
import com.bexai.bexaiinventory.presentation.viewmodel.SeleccionViewModel

@Composable
fun SeleccionSucursalScreen(
    operadorCodigo: String,
    onJornadaIniciada: () -> Unit,
    onCerrarSesion: () -> Unit,
    viewModel: SeleccionViewModel = viewModel()
) {
    LaunchedEffect(operadorCodigo) { viewModel.cargar(operadorCodigo) }

    val asignacion = viewModel.asignacion

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(subtitle = "Jornada")

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Tu asignación de hoy", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            when {
                viewModel.error != null -> {
                    Text(viewModel.error!!, color = MaterialTheme.colorScheme.error)
                }
                asignacion == null -> {
                    Text("Cargando asignación...")
                }
                else -> {
                    AsignacionCard(
                        etiqueta = "Centro de trabajo",
                        titulo = asignacion.sucursalNombre,
                        detalle = asignacion.direccion
                    )
                    AsignacionCard(
                        etiqueta = "Turno",
                        titulo = asignacion.turno.titulo,
                        detalle = asignacion.turno.horario
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = "Comenzar jornada",
                enabled = viewModel.puedeComenzar,
                onClick = { viewModel.comenzar(operadorCodigo, onJornadaIniciada) }
            )
            TextButton(onClick = onCerrarSesion) {
                Text("No soy yo / Cerrar sesión")
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun AsignacionCard(etiqueta: String, titulo: String, detalle: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(etiqueta, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(titulo, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(detalle, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Asignado, no editable",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}