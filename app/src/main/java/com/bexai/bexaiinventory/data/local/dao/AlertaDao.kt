package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.AlertaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertaDao {

    @Query("SELECT * FROM alertas ORDER BY atendida ASC")
    fun obtenerTodasLasAlertas(): Flow<List<AlertaEntity>>

    @Query("SELECT COUNT(*) FROM alertas")
    suspend fun contarAlertas(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAlertas(alertas: List<AlertaEntity>)

    @Query("UPDATE alertas SET atendida = 1 WHERE id = :alertaId")
    suspend fun marcarComoAtendida(alertaId: String)
}