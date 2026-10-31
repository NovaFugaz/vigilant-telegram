package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sucursales")
data class Sucursal(
    @PrimaryKey val id: Int,
    val nombre: String,
    val direccion: String
)