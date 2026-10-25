package com.bexai.bexaiinventory.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bexai.bexaiinventory.presentation.ui.components.FormTextField
import com.bexai.bexaiinventory.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel)
{
    val form by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Inicio de Sesión", fontSize = 40.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(15.dp))
        // correo
        FormTextField(
            value = form.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "Correo Electrónico",
            placeholder = "patito@gmail.cl",
            error = form.emailError
        )

        // contraseña
        FormTextField(
            value = form.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "contraseña",
            error = form.passwordError,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {viewModel.registro() }) {
            Text(text = "Iniciar Sesión", fontSize = 25.sp)
        }
    }
}

// para el preview
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LoginScreen(viewModel = LoginViewModel())
    }
}