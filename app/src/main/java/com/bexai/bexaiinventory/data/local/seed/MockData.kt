package com.bexai.bexaiinventory.data.local.seed

import com.bexai.bexaiinventory.data.local.entity.ProductoEntity

// me wa permitir dejar esto acá :3

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
}