package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.repository.ProductoRepository
import com.bexai.bexaiinventory.domain.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BodegaStockUiState(
    val productos: List<Producto> = emptyList(),
    val cargando: Boolean = true,
    val searchQuery: String = "",
    val selectedCategoria: String = "Todas",
    val selectedEstado: String = "Todos" // "Todos", "Normal", "Crítico", "Quiebre"
) {
    val categoriasDisponibles: List<String>
        get() = listOf("Todas") + productos.map { it.descripcion }.distinct().filter { it.isNotBlank() }

    val productosFiltrados: List<Producto>
        get() = productos.filter { producto ->
            // Filtro por Query
            val matchesQuery = searchQuery.isBlank() ||
                    producto.sku.contains(searchQuery, ignoreCase = true) ||
                    producto.nombre.contains(searchQuery, ignoreCase = true) ||
                    producto.descripcion.contains(searchQuery, ignoreCase = true)

            // Filtro por Categoría
            val matchesCategoria = selectedCategoria == "Todas" || producto.descripcion.equals(selectedCategoria, ignoreCase = true)

            // Filtro por Estado de Stock
            val isQuiebre = producto.stockTienda <= 0
            val isCritico = producto.stockTienda in 1..producto.stockMinimo
            val isNormal = producto.stockTienda > producto.stockMinimo

            val matchesEstado = when (selectedEstado) {
                "Quiebre" -> isQuiebre
                "Crítico" -> isCritico
                "Normal" -> isNormal
                else -> true
            }

            matchesQuery && matchesCategoria && matchesEstado
        }
}

class BodegaStockViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductoRepository(
        AppDataBase.getDatabase(application).productoDao()
    )

    private val _uiState = MutableStateFlow(BodegaStockUiState())
    val uiState: StateFlow<BodegaStockUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
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

    fun onCategoriaChange(categoria: String) {
        _uiState.update { it.copy(selectedCategoria = categoria) }
    }

    fun onEstadoChange(estado: String) {
        _uiState.update { it.copy(selectedEstado = estado) }
    }
}
