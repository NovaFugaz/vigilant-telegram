package com.bexai.bexaiinventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bexai.bexaiinventory.domain.model.AccionTarea
import com.bexai.bexaiinventory.domain.model.TipoTarea

@Entity(tableName = "tareas")
data class TareaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val operadorCodigo: String,
    val titulo: String,
    val detalle: String,
    val accion: AccionTarea,
    val tipo: TipoTarea
)