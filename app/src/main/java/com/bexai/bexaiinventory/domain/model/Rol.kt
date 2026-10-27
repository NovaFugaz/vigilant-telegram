package com.bexai.bexaiinventory.domain.model

enum class Rol(val cargo: String) {

    // para no usar solamente .TERRENO
    // ahora sería .TERRENO.cargo
    TERRENO("Operador en Terreno"),
    BODEGA("Encargado de Bodega"),
    GERENCIA("Gerencia Multi-tienda")
}