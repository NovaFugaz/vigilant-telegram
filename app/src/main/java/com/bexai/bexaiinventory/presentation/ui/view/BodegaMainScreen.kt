package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.presentation.ui.components.ActionSalida
import com.bexai.bexaiinventory.presentation.ui.navigation.tabsBodega
import com.bexai.bexaiinventory.presentation.viewmodel.JornadaEstado
import com.bexai.bexaiinventory.presentation.viewmodel.JornadaViewModel

@Composable
fun BodegaMainScreen(
    onSinJornada: () -> Unit,
    onAbrirEscaner: () -> Unit = {},
    jornadaViewModel: JornadaViewModel = viewModel()
) {
    val estado by jornadaViewModel.estado.collectAsState()

    when (val e = estado) {
        JornadaEstado.Cargando -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        // Al terminar la jornada vuelve al login
        JornadaEstado.SinJornada -> LaunchedEffect(Unit) { onSinJornada() }

        is JornadaEstado.Iniciada -> RoleScaffold(
            tabs = tabsBodega,
            info = "${e.detalle.sucursalNombre} · ${e.detalle.turno.titulo}",
            topBarActions = {
                ActionSalida(
                    descripcion = "Terminar jornada",
                    titulo = "¿Terminar jornada?",
                    mensaje = "Se cerrará tu jornada y volverás al inicio de sesión.",
                    textoConfirmar = "Terminar",
                    onConfirmar = { jornadaViewModel.terminarJornada() }
                )
            }
        ) { route ->
            when (route) {
                "bodega_stock" -> BodegaStockScreen(onAbrirEscaner = onAbrirEscaner)
                "bodega_diferencias" -> DiferenciasBodegaScreen()
                "bodega_reposicion" -> SolicitudReposicionScreen()
                else -> UnderConstructionScreen(title = route)
            }
        }
    }
}
