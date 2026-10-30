package com.bexai.bexaiinventory.domain.model

data class Ubicacion(
    val zona: String,
    val pasillo: String,
    val estante: String,
    val nivel: String
) {
    // Por cualquier cosa
    val codigoLegible: String
        get() = "Zona $zona • Pasillo $pasillo • Est. $estante • Niv. $nivel"

    // Identificador único estandarizado para validaciones o QR
    val codigoQrId: String
        get() = "LOC-$zona-$pasillo-$estante-$nivel"
}