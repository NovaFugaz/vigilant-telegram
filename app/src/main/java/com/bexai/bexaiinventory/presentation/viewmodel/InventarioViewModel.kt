package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductoRepository(
        AppDataBase.getDatabase(application).productoDao()
    )

    private val _uiState = MutableStateFlow(InventarioUiState())
    val uiState: StateFlow<InventarioUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedProductos()
            repository.obtenerProductos(sucursalId = 1).collect { lista ->
                _uiState.update { it.copy(productos = lista, cargando = false) }
            }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query, seleccionadoSku = null) }
    }

    fun onPasilloChange(pasillo: String) {
        _uiState.update { it.copy(pasillo = pasillo) }
    }

    fun onSeleccionar(sku: String) {
        _uiState.update { it.copy(seleccionadoSku = sku) }
    }

    fun rectificar(sku: String, nuevoStock: Int) {
        viewModelScope.launch {
            repository.actualizarStock(sku, nuevoStock)
            _uiState.update { it.copy(contados = it.contados + sku, seleccionadoSku = null) }
        }
    }
}