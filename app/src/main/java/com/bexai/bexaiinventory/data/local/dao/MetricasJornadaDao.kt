package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.MetricasJornadaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MetricasJornadaDao {

    @Query("SELECT * FROM metricas_jornada WHERE operadorCodigo = :codigo LIMIT 1")
    fun obtener(codigo: String): Flow<MetricasJornadaEntity?>

    @Query("SELECT COUNT(*) FROM metricas_jornada WHERE operadorCodigo = :codigo")
    suspend fun contar(codigo: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(metricas: MetricasJornadaEntity)
}