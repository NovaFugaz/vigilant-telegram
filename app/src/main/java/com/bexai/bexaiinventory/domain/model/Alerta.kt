package com.bexai.bexaiinventory.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Alerta(
    val id: String,
    val titulo: String,
    val mensaje: String,
    val tipo: TipoAlerta,
    // Para implementar esto añadí la dep de kotlinx, porque LocalDateTime.now() de Java está hasta una API vieja.
    // Y para eso tendría que "desugarizar la app" y no me siento preparada emocionalmente para eso.
    val fecha: LocalDate,
    val hora: LocalTime,
    val operador: Operador,
    val isLeida: Boolean = false
)
