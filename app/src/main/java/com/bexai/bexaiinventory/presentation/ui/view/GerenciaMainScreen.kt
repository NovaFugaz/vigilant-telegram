package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.runtime.Composable
import com.bexai.bexaiinventory.presentation.ui.components.ActionSalida
import com.bexai.bexaiinventory.presentation.ui.navigation.tabsGerencia

@Composable
fun GerenciaMainScreen(onCerrarSesion: () -> Unit) {
    RoleScaffold(
        tabs = tabsGerencia,
        topBarActions = {
            ActionSalida(
                descripcion = "Cerrar sesión",
                titulo = "¿Cerrar sesión?",
                mensaje = "Volverás a la pantalla de inicio de sesión.",
                textoConfirmar = "Cerrar sesión",
                onConfirmar = onCerrarSesion,
            )
        }
    ) { route ->
        when (route) {
            "gerencia_tablero" -> GerenciaTableroScreen()
            "gerencia_reportes" -> GerenciaReportesScreen()
            "gerencia_alertas" -> AlertasScreen()
            "gerencia_tiendas" -> GerenciaReportesScreen()
            "gerencia_auditoria" -> DiferenciasBodegaScreen()
            else -> UnderConstructionScreen(title = route)
        }
    }
}
