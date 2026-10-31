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

    // lo mismo para contar() en operadorDao
    @Query("SELECT COUNT(*) FROM jornadas")
    suspend fun contarSucursal(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarSucursal(centro: List<Sucursal>)
}