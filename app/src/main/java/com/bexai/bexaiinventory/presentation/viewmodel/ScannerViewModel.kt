package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.local.entity.ProductoEntity
import com.bexai.bexaiinventory.data.repository.ProductoRepository
import com.bexai.bexaiinventory.domain.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ScannerUiState(
    val codigoEscaneado: String? = null,
    val productoEncontrado: Producto? = null,
    val mostrarDialogoNuevo: Boolean = false,
    val mensajeExito: String? = null
)

class ScannerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductoRepository(
        AppDataBase.getDatabase(application).productoDao()
    )

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    fun onCodigoEscaneado(codigo: String, sucursalId: Int = 1) {
        // Evitamos procesar dos veces el mismo código si ya lo estamos mostrando
        if (_uiState.value.codigoEscaneado == codigo) return

        viewModelScope.launch {
            val producto = repository.buscarPorCodigo(codigo, sucursalId)

            if (producto != null) {
                // Producto EXISTENTE en Room
                _uiState.update {
                    it.copy(
                        codigoEscaneado = codigo,
                        productoEncontrado = producto,
                        mostrarDialogoNuevo = false
                    )
                }
            } else {
                // Producto NUEVO no registrado en Room -> Abrir Formulario
                _uiState.update {
                    it.copy(
                        codigoEscaneado = codigo,
                        productoEncontrado = null,
                        mostrarDialogoNuevo = true
                    )
                }
            }
        }
    }

    fun cerrarDialogo() {
        _uiState.update { it.copy(mostrarDialogoNuevo = false) }
    }

    fun guardarNuevoProducto(
        nombre: String,
        descripcion: String,
        stockInicial: Int,
        pasillo: String,
        sucursalId: Int = 1
    ) {
        val codigo = _uiState.value.codigoEscaneado ?: return

        viewModelScope.launch {
            val nuevoEntity = ProductoEntity(
                sku = codigo,
                ean13 = codigo,
                nombre = nombre,
                descripcion = descripcion,
                stockTienda = stockInicial,
                stockMinimo = 5,
                unidadMedida = "UN",
                valor = 0,
                pasillo = pasillo,
                estante = "1",
                nivel = "1",
                zona = "Principal",
                sucursalId = sucursalId
            )

            // Guardamos en Room
            repository.guardarProducto(nuevoEntity)

            // Actualizamos UI para mostrar que ahora ya existe el producto
            onCodigoEscaneado(codigo, sucursalId)
        }
    }
}