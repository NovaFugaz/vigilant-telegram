package com.bexai.bexaiinventory.domain.model

data class Producto(
    val sku: String,
    val ean13: String,
    val nombre: String,
    val descripcion: String,
    val stockTienda: Int,
    val stockMinimo: Int,
    val valor: Long, // Creo que deberíamos manejarlo, porque a la empresa le gustaría saberlo.
    val Ubicacion: Ubicacion
) {
}