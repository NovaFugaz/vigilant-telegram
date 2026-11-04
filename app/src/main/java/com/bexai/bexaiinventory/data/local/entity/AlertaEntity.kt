package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alertas")
data class AlertaEntity(
    @PrimaryKey
    val id: String,
    val tituloAlerta: String,
    val sku: String,
    val ean13: String,
    val nombreProducto: String,
    val stockActual: Int,
    val stockRequerido: Int,
    val ubicacionNombre: String,
    val severidadAlerta: String,
    val fechaIso: String,
    val horaIso: String,
    val atendida: Boolean = false
)

