package com.bexai.bexaiinventory.domain.model

data class Tarea(
    val id: Int,
    val titulo: String,
    val detalle: String,
    val accion: AccionTarea
)