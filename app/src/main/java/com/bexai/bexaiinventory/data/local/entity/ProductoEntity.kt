package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey
    val sku: String,
    val ean13: String,
    val nombre: String,
    val descripcion: String,
    val stockTienda: Int,
    val stockMinimo: Int,
    val unidadMedida: String, // 2 caracteres >.<
    val valor: Long,
    val pasillo: String,
    val estante: String,
    val nivel: String,
    val zona: String,
    val sucursalId: Int
)

