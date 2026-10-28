package com.bexai.bexaiinventory.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.bexai.bexaiinventory.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    logeado: () -> Unit, viewModel: LoginViewModel = viewModel()
){

    val estado by viewModel.uiState.collectAsState()
    LaunchedEffect(estado.loginExitoso) {
        if (estado.loginExitoso) logeado()
    }

    Column(modifier = Modifier.fillMaxSize().
    background(MaterialTheme.colorScheme.background).
    padding(24.dp),
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
            error = estado.codigoError
        )

        FormTextField(
            value = estado.pin,
            onValueChange = { viewModel.onPinChange(it) },
            label = "PIN (6 dígitos)",
            error = estado.pinError,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Text(text = "Seleccione su perfil",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))

        // las tarjetitas de los roles
        Rol.entries.forEach { rol ->
            RoleOptionCard(
                title = rol.cargo,
                selected = estado.rol == rol,
                onClick = { viewModel.onRolChange(rol) }
            )
            Spacer(Modifier.height(8.dp))
        }

        if (estado.rolError != null) {
            Text(
                text = estado.rolError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            text = "Iniciar Sesión",
            onClick = { viewModel.validar() }
        )
    }
}
