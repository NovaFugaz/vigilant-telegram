package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.repository.AlertaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlertasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AlertaRepository(
        AppDataBase.getDatabase(application).alertaDao()
    )

    private val _uiState = MutableStateFlow(AlertasUiState())
    val uiState: StateFlow<AlertasUiState> = _uiState.asStateFlow()

    init {
        cargarAlertas()
    }

    private fun cargarAlertas() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }

            // 1. Nos aseguramos de tener alertas de prueba en Room
            repository.seedAlertas()

            // 2. Escuchamos las alertas de Room en tiempo real
            repository.obtenerAlertas().collect { lista ->
                _uiState.update {
                    it.copy(
                        alertas = lista,
                        cargando = false
                    )
                }
            }
        }
    }

    fun onFiltroChange(filtro: String) {
        _uiState.update { it.copy(filtroSeleccionado = filtro) }
    }

    fun atenderAlerta(alertaId: String) {
        viewModelScope.launch {
            repository.atenderAlerta(alertaId)
        }
    }
}