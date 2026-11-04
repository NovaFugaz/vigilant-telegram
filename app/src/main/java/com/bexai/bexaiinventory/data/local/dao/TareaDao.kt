package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Query("SELECT * FROM tareas WHERE operadorCodigo = :codigo ORDER BY id")
    fun obtenerPorOperador(codigo: String): Flow<List<TareaEntity>>

    @Query("SELECT COUNT(*) FROM tareas WHERE operadorCodigo = :codigo")
    suspend fun contar(codigo: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tareas: List<TareaEntity>)
}