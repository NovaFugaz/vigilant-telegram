package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.Operador

@Dao
interface OperadorDao {

    @Query("SELECT * FROM operadores WHERE codigo = :codigo LIMIT 1")
    suspend fun findByCode(codigo: String): Operador?

    @Query("SELECT COUNT(*) FROM operadores")
    suspend fun contar(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarOperador(operador: Operador)
}