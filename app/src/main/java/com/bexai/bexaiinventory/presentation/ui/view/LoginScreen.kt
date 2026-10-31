package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bexai.bexaiinventory.domain.model.Rol
import com.bexai.bexaiinventory.presentation.ui.components.FormTextField
import com.bexai.bexaiinventory.presentation.ui.components.PrimaryButton
import com.bexai.bexaiinventory.presentation.ui.components.RoleOptionCard
import com.bexai.bexaiinventory.presentation.ui.components.TopBar
import com.bexai.bexaiinventory.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    logeado: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()
    val operador = estado.operador
    val camposActivos = !estado.cargando && operador == null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(subtitle = "Acceso")

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Inicio de Sesión", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(24.dp))

            FormTextField(
                value = estado.codigo,
                onValueChange = { viewModel.onCodigoChange(it) },
                label = "Código de operador",
                placeholder = "OP-0000",
                error = estado.codigoError,
                enabled = camposActivos
            )

            FormTextField(
                value = estado.pin,
                onValueChange = { viewModel.onPinChange(it) },
                label = "PIN (6 dígitos)",
                error = estado.pinError,
                enabled = camposActivos,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(16.dp))

            when {
                estado.cargando -> {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(8.dp))
                    Text("Buscando operador...")
                }

                operador != null -> {
                    RoleOptionCard(
                        title = operador.rol.cargo,
                        subtitle = "${operador.nombre} · ${operador.codigo}",
                        selected = true
                    )
                    Spacer(Modifier.height(16.dp))
                    PrimaryButton(text = "Continuar", onClick = logeado)
                }

                else -> {
                    PrimaryButton(
                        text = "Iniciar Sesión",
                        onClick = { viewModel.validar() }
                    )
                }
            }
            Spacer(Modifier.height(48.dp))
        }
    }
}