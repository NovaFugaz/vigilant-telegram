package com.bexai.bexaiinventory.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Alerta(
    val id: String,
    val tituloAlerta: String,
    val sku: String,
    val ean13: String,
    val nombreProducto: String,
    val stockActual: Int,
    val stockRequerido: Int,
    val ubicacionNombre: String,
    val severidad: SeveridadAlerta,
    val fecha: LocalDate,
    val hora: LocalTime,
    val atendida: Boolean = false
)