package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.domain.model.Turno
import com.bexai.bexaiinventory.presentation.ui.components.PrimaryButton
import com.bexai.bexaiinventory.presentation.ui.components.RoleOptionCard
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
import com.bexai.bexaiinventory.presentation.ui.theme.TextoNegro
import com.bexai.bexaiinventory.presentation.viewmodel.SeleccionViewModel

@Composable
fun SeleccionSucursalScreen(
    operadorCodigo: String,
    onJornadaIniciada: () -> Unit,
    onCerrarSesion: () -> Unit,
    viewModel: SeleccionViewModel = viewModel()
) {
    val sucursales by viewModel.sucursales.collectAsState()

    LaunchedEffect(operadorCodigo) { viewModel.precarga(operadorCodigo) }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(subtitle = "Jornada")

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Centro de trabajo", fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = TextoNegro)

            sucursales.forEach { sucursal ->
                RoleOptionCard(
                    title = sucursal.nombre,
                    subtitle = sucursal.direccion,
                    selected = viewModel.sucursalId == sucursal.id,
                    onClick = { viewModel.elegirSucursal(sucursal.id) }
                )
            }

            Text("Turno de trabajo", fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = TextoNegro,
                modifier = Modifier.padding(top = 12.dp))

            Turno.entries.forEach { turno ->
                RoleOptionCard(
                    title = turno.titulo,
                    subtitle = turno.horario,
                    selected = viewModel.turno == turno,
                    onClick = { viewModel.elegirTurno(turno) }
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = "Comenzar jornada",
                enabled = viewModel.puedeComenzar,
                onClick = { viewModel.comenzar(operadorCodigo, onJornadaIniciada) },
                modifier = Modifier.padding(40.dp)
            )
        }
    }
}