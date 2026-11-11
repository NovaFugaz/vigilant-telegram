package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.ui.components.AccesoRapidoCard
import com.bexai.bexaiinventory.presentation.ui.components.MetricasCard
import com.bexai.bexaiinventory.presentation.ui.components.PrimaryButton
import com.bexai.bexaiinventory.presentation.ui.components.SeccionTitulo
import com.bexai.bexaiinventory.presentation.ui.components.TareaCard
import com.bexai.bexaiinventory.presentation.viewmodel.HomeOperadorViewModel
import com.bexai.bexaiinventory.domain.model.TipoTarea

@Composable
fun HomeOperadorScreen(
    operadorCodigo: String,
    onAbrirScanner: () -> Unit = {},
    onAbrirBusqueda: () -> Unit = {},
    onAbrirConteo: () -> Unit = {},
    viewModel: HomeOperadorViewModel = viewModel(
        key = operadorCodigo,
        factory = HomeOperadorViewModel.factory(operadorCodigo)
    )
) {
    val state by viewModel.uiState.collectAsState()
    val m = state.metricas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // MÉTRICAS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SeccionTitulo("Métricas de la jornada")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricasCard("Contados", "${m.contados}", Modifier.weight(1f))
                MetricasCard("Revisión", "${m.revision}", Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricasCard(
                    titulo = "Discrepancias",
                    valor = "${m.incongruencia}",
                    modifier = Modifier.weight(1f),
                    esAlerta = m.incongruencia > 0
                )
                MetricasCard(
                    titulo = "Pasillos OK",
                    valor = "${m.pasillosOk} / ${m.pasillosTotal}",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // ACCESOS RÁPIDOS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SeccionTitulo("Accesos operativos rápidos")
            PrimaryButton(
                text = "Abrir Escáner",
                onClick = onAbrirScanner,
                modifier = Modifier.fillMaxWidth()
            )
            AccesoRapidoCard(
                titulo = "Búsqueda e Ingreso Manual",
                descripcion = "Para etiquetas rotas o no legibles",
                icono = Icons.Default.Keyboard,
                onClick = onAbrirBusqueda
            )
            AccesoRapidoCard(
                titulo = "Conteo y Rectificación",
                descripcion = "Ajustes directos de stock por pasillo",
                icono = Icons.Default.Tune,
                onClick = onAbrirConteo
            )
        }

        // TAREAS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SeccionTitulo("Tareas asignadas del turno")
            state.tareas.forEach { tarea ->
                TareaCard(tarea = tarea, onClick = {
                    when (tarea.tipo) {
                        TipoTarea.CONTEO,
                        TipoTarea.RECONTEO -> onAbrirConteo()
                        TipoTarea.RECEPCION -> onAbrirScanner()
                    }
                })
            }
        }
    }
}