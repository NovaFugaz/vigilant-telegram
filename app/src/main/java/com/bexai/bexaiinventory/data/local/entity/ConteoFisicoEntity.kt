package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conteos_fisicos")
data class ConteoFisicoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sku: String,
    val stockTeorico: Int,
    val conteoFisico: Int,
    val motivo: String, // "Merma / Daño", "Error previo", "Traspaso no registrado" o whatever, xD
    val fechaIso: String,
    val sucursalId: Int
)
