package com.bexai.bexaiinventory.domain.model

sealed class UnidadMedida(
    val codigo: String,
    val nombreUnidad: String,
    val permiteDecimal: Boolean) {

    object Unidades : UnidadMedida("UN", "Unidades", permiteDecimal = false)
    object Kilogramos: UnidadMedida("KG", "Kilogramos", permiteDecimal = true)
    object Cajas: UnidadMedida("CJ", "Cajas", false)
    object Litros: UnidadMedida("LT", "Litros", permiteDecimal = true)

    // Quizá deberíamos dejar un objeto personalidzado para cuando no haya.

    companion object {
        fun fromString(codigo: String): UnidadMedida {
            return when (codigo.uppercase()) {
                "UN" -> Unidades
                "KG" -> Kilogramos
                "CJ" -> Cajas
                "LT" -> Litros
                else -> Unidades // Default :3
            }
        }
    }

}