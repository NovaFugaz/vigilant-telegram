package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos WHERE sucursalId = :sucursalId")
    fun obtenerProductosPorSucursal(sucursalId: Int): Flow<List<ProductoEntity>>

    @Query("SELECT * FROM productos WHERE (sku = :codigo OR ean13 = :codigo) AND sucursalId = :sucursalId LIMIT 1")
    suspend fun buscarPorCodigo(codigo: String,  sucursalId: Int): ProductoEntity?

    @Query("SELECT COUNT(*) FROM productos")
    suspend fun contarProductos(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProductos(productos: List<ProductoEntity>)
}