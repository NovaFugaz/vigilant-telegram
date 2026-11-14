package com.bexai.bexaiinventory.data.mapper

import com.bexai.bexaiinventory.data.local.entity.ProductoEntity
import com.bexai.bexaiinventory.domain.model.UnidadMedida
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ProductoMapperTest {

    @Test
    fun toDomainMapsProductoEntityCorrectly() {
        val entity = ProductoEntity(
            sku = "SKU123",
            ean13 = "7791234567890",
            nombre = "Producto Test",
            descripcion = "Descripción de prueba",
            stockTienda = 50,
            stockMinimo = 10,
            unidadMedida = "UN",
            valor = 1500,
            pasillo = "P1",
            estante = "E2",
            nivel = "N3",
            zona = "Z1",
            sucursalId = 1
        )

        val domain = entity.toDomain()

        domain.sku shouldBe "SKU123"
        domain.ean13 shouldBe "7791234567890"
        domain.nombre shouldBe "Producto Test"
        domain.descripcion shouldBe "Descripción de prueba"
        domain.stockTienda shouldBe 50
        domain.stockMinimo shouldBe 10
        domain.unidadMedida shouldBe UnidadMedida.Unidades
        domain.valor shouldBe 1500
        domain.Ubicacion.zona shouldBe "Z1"
        domain.Ubicacion.pasillo shouldBe "P1"
        domain.Ubicacion.estante shouldBe "E2"
        domain.Ubicacion.nivel shouldBe "N3"
    }
}
