package com.bexai.bexaiinventory.data.local.seed

import com.bexai.bexaiinventory.data.local.entity.AlertaEntity
import com.bexai.bexaiinventory.data.local.entity.ProductoEntity

// me wa permitir dejar esto acá :3
// esta cuestión debería ser un componente para la evaluación, considerando lo útil que es en reducir código de otro lado, xd.

object MockData {
    val productosIniciales = listOf(
        ProductoEntity(
            sku = "SKU-7704219",
            ean13 = "7801234567890",
            nombre = "Esmeril Angular 4 1/2\" 850W Dewalt",
            descripcion = "Herramientas eléctricas pesadas",
            stockTienda = 24,
            stockMinimo = 10,
            unidadMedida = "UN",
            valor = 148420,
            pasillo = "B-04",
            estante = "3",
            nivel = "2",
            zona = "Principal",
            sucursalId = 1
        ),
        ProductoEntity(
            sku = "SKU-8841920",
            ean13 = "7809482100412",
            nombre = "Taladro Percutor Inalámbrico 20V Dewalt",
            descripcion = "Motor Brushless Industrial",
            stockTienda = 3,
            stockMinimo = 8,
            unidadMedida = "UN",
            valor = 95000,
            pasillo = "A-12",
            estante = "1",
            nivel = "1",
            zona = "Principal",
            sucursalId = 1
        ),
        ProductoEntity(
            sku = "SKU-3361284",
            ean13 = "7805541299831",
            nombre = "Disco de Corte Metal 4 1/2\" Norton (x10)",
            descripcion = "Pack x10 unidades extra fino",
            stockTienda = 0,
            stockMinimo = 25,
            unidadMedida = "CJ",
            valor = 12500,
            pasillo = "B-02",
            estante = "2",
            nivel = "1",
            zona = "Principal",
            sucursalId = 1
        )
    )

    // Dentro de tu objeto MockData en data/local/seed/MockData.kt

    val alertasIniciales = listOf(
        AlertaEntity(
            id = "ALT-001",
            tituloAlerta = "QUIEBRE ACTIVO",
            sku = "SKU-3361284",
            ean13 = "7805541299831",
            nombreProducto = "Disco de Corte Metal 4 1/2\" Norton",
            stockActual = 0,
            stockRequerido = 25,
            ubicacionNombre = "Santiago Centro Express - Pasillo 04-B",
            severidadAlerta = "QUIEBRE_CRITICO",
            fechaIso = "2024-05-20",
            horaIso = "09:30:00"
        ),
        AlertaEntity(
            id = "ALT-002",
            tituloAlerta = "STOCK CRÍTICO INMINENTE",
            sku = "SKU-8841920",
            ean13 = "7809482100412",
            nombreProducto = "Taladro Percutor Inalámbrico 20V Dewalt",
            stockActual = 3,
            stockRequerido = 8,
            ubicacionNombre = "Bodega Central Pudahuel - Rack 12-C",
            severidadAlerta = "STOCK_CRITICO_INMINENTE",
            fechaIso = "2024-05-20",
            horaIso = "10:15:00"
        ),
        AlertaEntity(
            id = "ALT-003",
            tituloAlerta = "DESCALCE CONTEO FÍSICO",
            sku = "SKU-7704219",
            ean13 = "7801234567890",
            nombreProducto = "Esmeril Angular 4 1/2\" Dewalt",
            stockActual = 21,
            stockRequerido = 24,
            ubicacionNombre = "Bodega Central - Pasillo B-04",
            severidadAlerta = "DESCALCE_CONTEO",
            fechaIso = "2024-05-20",
            horaIso = "11:00:00"
        ),
        AlertaEntity(
            id = "ALT-004",
            tituloAlerta = "ADVERTENCIA PREVENTIVA",
            sku = "SKU-1029482",
            ean13 = "7801234599999",
            nombreProducto = "Guantes Nitrilo Industrial Talla L",
            stockActual = 6,
            stockRequerido = 15,
            ubicacionNombre = "Sucursal Viña del Mar",
            severidadAlerta = "ADVERTENCIA_PREVENTIVA",
            fechaIso = "2024-05-20",
            horaIso = "11:30:00"
        )
    )
}