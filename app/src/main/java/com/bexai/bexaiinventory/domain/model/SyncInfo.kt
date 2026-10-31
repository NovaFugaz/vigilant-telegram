package com.bexai.bexaiinventory.domain.model

import kotlinx.datetime.LocalDateTime

data class SyncInfo(
    val ultimaSincronizacion: LocalDateTime? = null,
    val isSincronizando: Boolean = false,
    // Por mientras voy a dejar esto acá, pero acá tiene que ser un sealed class.
    // Tipo, acá va a quedar -> mensajeEstado : MensajeEstado
    val mensajeEstado: String,
)
