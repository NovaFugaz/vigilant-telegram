package com.bexai.bexaiinventory.domain.model

data class MetricasJornada(
    val contados: Int = 0,
    val revision: Int = 0,
    val incongruencia: Int = 0,
    val pasillosOk: Int = 0,
    val pasillosTotal: Int = 0
)