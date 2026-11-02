package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bexai.bexaiinventory.presentation.ui.components.ActionSalida
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
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
                onConfirmar = onCerrarSesion
            )
        }
    )
}