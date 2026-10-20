package com.bexai.bexainventory.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bexai.bexainventory.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel)
{
    val form by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Inicio de Sesión", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = form.email,
            label = { Text(text = "Correo electronico") },
            placeholder = { Text(text = "patito@gmail.cl")},
            onValueChange = { viewModel.onEmailChange(it) },
            isError = form.emailError != null,
            supportingText =
                {
                    if (form.emailError != null)
                    {
                        Text(text = form.emailError!!, color = MaterialTheme.colorScheme.error)
                    }
                }
        )

        OutlinedTextField(
            value = form.password,
            label = { Text(text = "Contraseña") },
            onValueChange = { viewModel.onPasswordChange(it)},
            isError = form.passwordError != null,
            visualTransformation = PasswordVisualTransformation(),
            supportingText = {
                if (form.passwordError != null){
                    Text(text = form.passwordError!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {viewModel.registro()}, colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF97316),
            contentColor = Color.White
        )) {
            Text(text = "Iniciar Sesion", fontSize = 25.sp)
        }
    }
}

// ni idea luego quizas esto se quita
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LoginScreen(viewModel = LoginViewModel())
    }
}