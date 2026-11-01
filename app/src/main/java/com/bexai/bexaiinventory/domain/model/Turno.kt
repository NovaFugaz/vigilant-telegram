package com.bexai.bexaiinventory.domain.model

enum class Turno(val titulo: String, val horario: String)
{
    MANANA("Turno Mañana", "08:00 - 16:30"),
    TARDE("Turno Tarde", "16:00 - 00:00")
}