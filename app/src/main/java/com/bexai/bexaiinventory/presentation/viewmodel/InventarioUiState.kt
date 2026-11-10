package com.bexai.bexaiinventory.presentation.viewmodel

import com.bexai.bexaiinventory.domain.model.Producto

data class InventarioUiState(
    val productos: List<Producto> = emptyList(),
    val query: String = "",
    val pasillo: String = "Todos",
    val seleccionadoSku: String? = null,
    val contados: Set<String> = emptySet(), // skus rectificados en esta sesión
    val cargando: Boolean = true
) {
    // chips de pasillo
    val pasillos: List<String>
        get() = listOf("Todos") + productos.map { it.Ubicacion.pasillo }.distinct().sorted()

    // filtra por texto (sku, ean, nombre) y por pasillo
    val filtrados: List<Producto>
        get() = productos.filter { p ->
            val coincideTexto = p.nombre.contains(query, ignoreCase = true) ||
                    p.sku.contains(query, ignoreCase = true) ||
                    p.ean13.contains(query, ignoreCase = true)
            val coincidePasillo = pasillo == "Todos" || p.Ubicacion.pasillo == pasillo
            coincideTexto && coincidePasillo
        }

    val seleccionado: Producto?
        get() = productos.find { it.sku == seleccionadoSku }
}