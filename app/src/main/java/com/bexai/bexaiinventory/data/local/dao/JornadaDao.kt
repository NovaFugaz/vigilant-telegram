package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.Jornada
import com.bexai.bexaiinventory.data.local.entity.JornadaDetalle
import kotlinx.coroutines.flow.Flow

@Dao
interface JornadaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarJornada(jornada: Jornada)

    // Lectura simple, para precargar la selección
    @Query("SELECT * FROM jornadas WHERE operadorCodigo = :codigo")
    suspend fun obtenerJornada(codigo: String): Jornada?

    // Para la cabecera del MainScreen
    @Query(
        """SELECT j.operadorCodigo, s.nombre AS sucursalNombre, j.turno
           FROM jornadas j INNER JOIN sucursales s ON s.id = j.sucursalId
           WHERE j.operadorCodigo = :codigo"""
    )
    fun obtenerJornadaActual(codigo: String): Flow<JornadaDetalle?>
}