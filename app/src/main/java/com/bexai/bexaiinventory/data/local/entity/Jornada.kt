package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bexai.bexaiinventory.domain.model.Turno

@Entity(tableName = "jornadas")
data class Jornada(
    @PrimaryKey val operadorCodigo: String,
    val sucursalId: Int,
    val turno: Turno
)

data class JornadaDetalle(
    val operadorCodigo: String,
    val sucursalNombre: String,
    val turno: Turno
)
