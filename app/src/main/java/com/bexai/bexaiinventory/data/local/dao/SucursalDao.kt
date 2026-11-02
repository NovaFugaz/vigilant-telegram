package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.Sucursal
import kotlinx.coroutines.flow.Flow

@Dao
interface SucursalDao {

    @Query("SELECT * FROM sucursales ORDER BY nombre")
    fun obtenerTodos(): Flow<List<Sucursal>>

    @Query("SELECT * FROM sucursales WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): Sucursal?

    @Query("SELECT COUNT(*) FROM sucursales")
    suspend fun contarSucursal(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarSucursal(centro: List<Sucursal>)
}