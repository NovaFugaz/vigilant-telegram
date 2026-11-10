package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.local.dao.DiferenciaStockInfo
import com.bexai.bexaiinventory.data.repository.DiferenciasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DiferenciasBodegaUiState(
    val descalces: List<DiferenciaStockInfo> = emptyList(),
    val cargando: Boolean = true,
    val searchQuery: String = "",
    val filtroMotivo: String = "Todos", // "Todos", "Merma / Daño", "Error previo", "Traspaso no registrado"
    val filtroTipo: String = "Todos" // "Todos", "Faltantes (-)", "Sobrantes (+)"
) {
    val descalcesFiltrados: List<DiferenciaStockInfo>
        get() = descalces.filter { item ->
            val matchesQuery = searchQuery.isBlank() ||
                    item.sku.contains(searchQuery, ignoreCase = true) ||
                    item.nombreProducto.contains(searchQuery, ignoreCase = true)

            val matchesMotivo = filtroMotivo == "Todos" || item.motivo.equals(filtroMotivo, ignoreCase = true)

            val matchesTipo = when (filtroTipo) {
                "Faltantes (-)" -> item.diferencia < 0
                "Sobrantes (+)" -> item.diferencia > 0
                else -> true
            }

            matchesQuery && matchesMotivo && matchesTipo
        }
}

class DiferenciasBodegaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DiferenciasRepository(
        AppDataBase.getDatabase(application).conteoFisicoDao()
    )

    private val _uiState = MutableStateFlow(DiferenciasBodegaUiState())
    val uiState: StateFlow<DiferenciasBodegaUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            repository.seedConteos()
            repository.obtenerDescalces(sucursalId = 1).collect { lista ->
                _uiState.update {
                    it.copy(
                        descalces = lista,
                        cargando = false
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onFiltroMotivoChange(motivo: String) {
        _uiState.update { it.copy(filtroMotivo = motivo) }
    }

    fun onFiltroTipoChange(tipo: String) {
        _uiState.update { it.copy(filtroTipo = tipo) }
    }
}
