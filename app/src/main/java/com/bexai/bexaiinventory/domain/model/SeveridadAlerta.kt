package com.bexai.bexaiinventory.domain.model

enum class SeveridadAlerta {
    QUIEBRE_CRITICO,         // Rojo
    STOCK_CRITICO_INMINENTE, // Naranja
    DESCALCE_CONTEO,         // Amarillo
    ADVERTENCIA_PREVENTIVA   // Azul
}