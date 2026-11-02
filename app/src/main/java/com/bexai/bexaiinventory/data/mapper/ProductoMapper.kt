package com.bexai.bexaiinventory.data.mapper

import com.bexai.bexaiinventory.data.local.entity.ProductoEntity
import com.bexai.bexaiinventory.domain.model.Producto
import com.bexai.bexaiinventory.domain.model.Ubicacion
import com.bexai.bexaiinventory.domain.model.UnidadMedida

// Creo que Freddy no nos ha pasado esto. :z
// El Mapper se usa para convertir un Entity en un objeto tipo Domain
// Al Entity lo habíamos pasado con String o Int, pero acá el Mapper "remapea" todo.
// Dejamos así la interfaz separada de la base de datos :3

fun ProductoEntity.toDomain(): Producto {
    return Producto(
        sku = sku,
        ean13 = ean13,
        nombre = nombre,
        descripcion = descripcion,
        stockTienda = stockTienda,
        stockMinimo = stockMinimo,
        unidadMedida = UnidadMedida.fromString(unidadMedida),
        valor = valor,
        Ubicacion = Ubicacion(
            zona = zona,
            pasillo = pasillo,
            estante = estante,
            nivel = nivel
        )
    )
}