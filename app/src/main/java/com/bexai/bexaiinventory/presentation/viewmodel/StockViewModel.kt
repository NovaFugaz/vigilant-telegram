package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.repository.ProductoRepository
import com.bexai.bexaiinventory.domain.model.SyncInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class StockViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = ProductoRepository(
        AppDataBase.getDatabase(application).productoDao()
    )

    private val _uiState = MutableStateFlow(StockUiState())
    val uiState: StateFlow<StockUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos(){
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }

        // Seedear los datos de prueba en room if needed.
        repository.seedProductos()

        repository.obtenerProductos(sucursalId = 1).collect { lista ->
            _uiState.update {
                it.copy(
                    productos = lista,
                    cargando = false
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onFilterChange(filtro: String) {
        _uiState.update { it.copy(selectedFilter = filtro) }
    }

    @OptIn(ExperimentalTime::class)
    fun actualizarSincronizacion() {
        val ahora = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        _uiState.update {
            it.copy(
                syncInfo = SyncInfo(
                    ultimaSincronizacion = ahora,
                    isSincronizando = false,
                    mensajeEstado = "Sincronización Exitosa"
                )
            )
        }
    }
}