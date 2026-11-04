package com.bexai.bexaiinventory.presentation.viewmodel

import com.bexai.bexaiinventory.domain.model.Alerta
import com.bexai.bexaiinventory.domain.model.SeveridadAlerta

data class AlertasUiState(
    val alertas: List<Alerta> = emptyList(),
    val filtroSeleccionado: String = "Todos", // "Todos", "Quiebres", "Críticos", "Descalces"
    val cargando: Boolean = true
) {
    val alertasFiltradas: List<Alerta>
        get() = alertas.filter { alerta ->
            when (filtroSeleccionado) {
                "Quiebres" -> alerta.severidad == SeveridadAlerta.QUIEBRE_CRITICO
                "Críticos" -> alerta.severidad == SeveridadAlerta.STOCK_CRITICO_INMINENTE
                "Descalces" -> alerta.severidad == SeveridadAlerta.DESCALCE_CONTEO
                else -> true // "Todos"
            }
        }
}