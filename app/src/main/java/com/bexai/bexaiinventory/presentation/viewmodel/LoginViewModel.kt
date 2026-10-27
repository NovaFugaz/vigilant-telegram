package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.domain.model.LoginModel
import com.bexai.bexaiinventory.domain.model.Rol
import com.bexai.bexaiinventory.domain.repository.OperadorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// se lo copie al freddy
class LoginViewModel(application: Application): AndroidViewModel(application)
{
    private val repository = OperadorRepository(
        AppDataBase.getDatabase(application).operadorDao()
    )
    private val _uiState = MutableStateFlow(LoginModel())
    val uiState: StateFlow<LoginModel> = _uiState.asStateFlow()

    // esto es solo que lo explique en el dao, para la funcion contar()
    init {
        viewModelScope.launch { repository.datitos() }
    }

    // codigo del funcionario
    fun onCodigoChange(nuevoC: String){
        _uiState.update { it.copy(codigo = nuevoC.uppercase().trim(), codigoError = null) }
    }

    // para el pin
    fun onPinChange(nuevoP: String){
        val pin = nuevoP.filter { it.isDigit() }.take(6)
        _uiState.update { it.copy(pin = pin, pinError = null) }
    }

    // para el rol
    fun onRolChange(rol: Rol){
        _uiState.update { it.copy(rol = rol, rolError = null) }
    }


    fun validar(){
        val estado = _uiState.value

        if(estado.codigo.isBlank()) {
            _uiState.update { it.copy(codigoError = "Ingrese su código de operador") }
            return
        }

        // creo que era 6 digitos en el figma ??? creo que no se especificaba
        if(estado.pin.length != 6) {
            _uiState.update { it.copy(pinError = "El PIN debe tener 6 dígitos") }
            return
        }

        viewModelScope.launch {
            val operador = repository.find(estado.codigo)
            when {
                operador == null ->
                    _uiState.update { it.copy(codigoError = "Operador no encontrado") }
                operador.pin != estado.pin ->
                    _uiState.update { it.copy(pinError = "PIN incorrecto") }
                operador.rol != estado.rol ->
                    _uiState.update { it.copy(rolError = "Código de operador no registrado") }
                else ->
                    _uiState.update { it.copy(loginExitoso = true) }
            }
        }
    }
}