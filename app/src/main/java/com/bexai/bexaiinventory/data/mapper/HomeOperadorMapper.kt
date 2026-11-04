package com.bexai.bexaiinventory.data.mapper

import com.bexai.bexaiinventory.data.local.entity.MetricasJornadaEntity
import com.bexai.bexaiinventory.data.local.entity.TareaEntity
import com.bexai.bexaiinventory.domain.model.MetricasJornada
import com.bexai.bexaiinventory.domain.model.Tarea

fun MetricasJornadaEntity.toDomain() = MetricasJornada(
    contados = contados,
    revision = revision,
    incongruencia = incongruencia,
    pasillosOk = pasillosOk,
    pasillosTotal = pasillosTotal
)

fun TareaEntity.toDomain() = Tarea(
    id = id,
    titulo = titulo,
    detalle = detalle,
    accion = accion
)