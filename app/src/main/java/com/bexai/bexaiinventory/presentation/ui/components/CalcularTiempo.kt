package com.bexai.bexaiinventory.presentation.ui.components

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// Creo que esto debería ir en un paquete utils, uwu
// A mis 35 aprendí que puedo crear funciones de extensión para usar a necesidad, xd.
// Voy a dejar esto con muchísima desconfianza y le preguntaré al Freddy por esto, uwu.

@OptIn(ExperimentalTime::class)
fun LocalDateTime.formatearTiempoTranscurrido(): String {
    val ahora = Clock.System.now()
    val instanteGuardado = this.toInstant(TimeZone.currentSystemDefault()) // esta línea sí es bonita :3
    val diferenciaSegundos = (ahora - instanteGuardado).inWholeSeconds

    return when {
        diferenciaSegundos < 60 -> "hace 0 min"
        diferenciaSegundos < 3600 -> "hace ${diferenciaSegundos/60} min"
        diferenciaSegundos < 86400 -> "hace ${diferenciaSegundos/3600} hrs"
        else -> "hace ${diferenciaSegundos/86400} día"
    }
}