package com.bexai.bexainventory.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.bexai.bexainventory.model.LoginModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel()
{
    private val _uiState = MutableStateFlow(LoginModel())
    val uiState : StateFlow<LoginModel> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String)
    {
        _uiState.update { it.copy(email = newEmail, emailError = null) }
    }

    fun onPasswordChange(newPsw: String)
    {
        _uiState.update { it.copy(email = newPsw, passwordError = null) }
    }

    private fun validar()
    {
        val emailValido = _uiState.value.email.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()

        val paswValida = _uiState.value.password.isNotBlank()
                && _uiState.value.password.length>8
    }

    fun registro()
    {
        val emailValido = _uiState.value.email.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()

        val paswValida = _uiState.value.password.isNotBlank()
                && _uiState.value.password.length>8

        if(!emailValido)
        {
            _uiState.update { it.copy(emailError = "Correo electronico invalido") }
        }

        if(!paswValida)
        {
            _uiState.update { it.copy(passwordError = "La contraseña debe ser tener al menos 8 caracteres") }
        }
    }


}