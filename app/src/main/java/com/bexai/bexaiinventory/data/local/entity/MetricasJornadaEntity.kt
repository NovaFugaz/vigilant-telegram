package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metricas_jornada")
data class MetricasJornadaEntity(
    @PrimaryKey val operadorCodigo: String,
    val contados: Int,
    val revision: Int,
    val incongruencia: Int,
    val pasillosOk: Int,
    val pasillosTotal: Int
)