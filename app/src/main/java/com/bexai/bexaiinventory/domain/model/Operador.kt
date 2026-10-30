package com.bexai.bexaiinventory.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

// Este Operador necesita mejora, es simplemente para poder añadirlo a alerta.
data class Operador(
    val id: String,
    val pNombre: String,
    val appaterno: String,
    val apmaterno: String,
    val fechaIngreso: LocalDate,
    val ultimoLogin: LocalDateTime
) {
}