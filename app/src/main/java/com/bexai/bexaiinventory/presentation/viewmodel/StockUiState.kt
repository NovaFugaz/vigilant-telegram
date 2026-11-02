package com.bexai.bexaiinventory.presentation.viewmodel

import com.bexai.bexaiinventory.domain.model.Producto
import com.bexai.bexaiinventory.domain.model.SyncInfo

data class StockUiState(
    val productos: List<Producto> = emptyList(),
    val searchQuery : String = "",
    val selectedFilter: String = "Todos",
    val syncInfo: SyncInfo = SyncInfo(mensajeEstado = ""),
    val cargando: Boolean = true,
    ){
    val productosFiltrados: List<Producto>
        get() = productos.filter { producto ->
            val coincideBusqueda = producto.nombre.contains(searchQuery, ignoreCase = true) ||
                    producto.sku.contains(searchQuery, ignoreCase = true) ||
                    producto.ean13.contains(searchQuery, ignoreCase = true)

            val coincideFiltro = when (selectedFilter) {
                "Crítico" -> producto.stockTienda in 1..producto.stockMinimo
                "Quiebre" -> producto.stockTienda == 0
                else -> true // "Todos"
            }

            coincideBusqueda && coincideFiltro
        }
}
