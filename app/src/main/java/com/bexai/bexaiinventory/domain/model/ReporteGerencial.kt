package com.bexai.bexaiinventory.domain.model

data class ReporteGerencialSummary(
    val valorStockTotal: Double,
    val capacidadStockUnidades: Int,
    val unidadesRedTotales: Int,
    val capacidadRedTotales: Int,
    val sucursalesActivas: Int,
    val porcentajeOcupacionRed: Int,
    val porcentajeCuadratura: Double,
    val variacionCuadratura: Double,
    val metaCuadratura: Double,
    val alertasCriticasCount: Int,
    val alertasQuiebresCount: Int
)

data class CapacidadCentro(
    val id: Int,
    val nombre: String,
    val ocupado: Int,
    val capacidadTotal: Int,
    val porcentaje: Int,
    val colorEstadoHex: String
)

data class ReporteItemTienda(
    val sucursalId: Int,
    val nombreTienda: String,
    val valorStock: Double,
    val unidadesStock: Int,
    val valorMermas: Double,
    val cantidadMermas: Int,
    val porcentajeDescalce: Double,
    val montoDescalce: Double,
    val quiebresCount: Int
)

data class BalanceAnioVsMes(
    val mesActualValorStock: Double,
    val mesAnteriorValorStock: Double,
    val anioActualValorStock: Double,
    val variacionMesPorcentaje: Double,
    val variacionAnioPorcentaje: Double
)
