package com.bexai.bexaiinventory.data.repository

import com.bexai.bexaiinventory.data.local.dao.ProductoDao
import com.bexai.bexaiinventory.data.local.seed.MockData
import com.bexai.bexaiinventory.data.mapper.toDomain
import com.bexai.bexaiinventory.domain.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoDao: ProductoDao
) {
    suspend fun seedProductos() {
        if (productoDao.contarProductos() == 0) {
            productoDao.insertarProductos(MockData.productosIniciales)
        }
    }

    fun obtenerProductos(sucursalId: Int): Flow<List<Producto>> {
        return productoDao.obtenerProductosPorSucursal(sucursalId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    suspend fun buscarPorCodigo(codigo: String, sucursalId: Int): Producto? {
        return productoDao.buscarPorCodigo(codigo, sucursalId)?.toDomain()
    }
}