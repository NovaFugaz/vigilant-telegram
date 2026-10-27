package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bexai.bexaiinventory.domain.model.Rol

@Entity(tableName = "operadores")
data class Operador(
    // no se me parece que era String en el figma
    @PrimaryKey val codigo: String,
    val nombre: String,
    val pin: String,
    val rol: Rol
)