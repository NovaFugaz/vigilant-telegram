package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.SolicitudReposicionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudReposicionDao {

    @Query("SELECT * FROM solicitudes_reposicion WHERE sucursalId = :sucursalId ORDER BY id DESC")
    fun obtenerSolicitudes(sucursalId: Int): Flow<List<SolicitudReposicionEntity>>

    @Query("SELECT COUNT(*) FROM solicitudes_reposicion")
    suspend fun contarSolicitudes(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarSolicitudes(solicitudes: List<SolicitudReposicionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarSolicitud(solicitud: SolicitudReposicionEntity)
}
