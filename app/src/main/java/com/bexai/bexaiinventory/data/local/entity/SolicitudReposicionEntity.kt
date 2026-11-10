package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes_reposicion")
data class SolicitudReposicionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sku: String,
    val nombreProducto: String,
    val cantidad: Int,
    val destino: String, // "Bodega Central", "Proveedor Directo" (De dónde estamos pidiendo la repo)
    val prioridad: String, // "Normal", "Urgente"
    val observacion: String,
    val estado: String = "Pendiente", // "Pendiente", "Aprobada", "En Tránsito"
    val fechaIso: String,
    val sucursalId: Int
)
