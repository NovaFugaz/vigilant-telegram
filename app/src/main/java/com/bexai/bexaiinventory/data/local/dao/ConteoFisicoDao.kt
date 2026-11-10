package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.ConteoFisicoEntity
import kotlinx.coroutines.flow.Flow

data class DiferenciaStockInfo(
    val id: Int,
    val sku: String,
    val nombreProducto: String,
    val descripcion: String,
    val stockTeorico: Int,
    val conteoFisico: Int,
    val diferencia: Int,
    val motivo: String,
    val fechaIso: String,
    val pasillo: String,
    val estante: String,
    val nivel: String,
    val zona: String
)

@Dao
interface ConteoFisicoDao {

    // Poggers query, lal, la weá, sadfgsdfg
    @Query("""
        SELECT c.id AS id, c.sku AS sku, p.nombre AS nombreProducto, p.descripcion AS descripcion,
               c.stockTeorico AS stockTeorico, c.conteoFisico AS conteoFisico, 
               (c.conteoFisico - c.stockTeorico) AS diferencia, c.motivo AS motivo, c.fechaIso AS fechaIso,
               p.pasillo AS pasillo, p.estante AS estante, p.nivel AS nivel, p.zona AS zona
        FROM conteos_fisicos c
        INNER JOIN productos p ON c.sku = p.sku
        WHERE c.sucursalId = :sucursalId AND c.stockTeorico != c.conteoFisico
        ORDER BY c.id DESC
    """)
    fun obtenerDescalces(sucursalId: Int): Flow<List<DiferenciaStockInfo>>

    @Query("SELECT COUNT(*) FROM conteos_fisicos")
    suspend fun contarConteos(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarConteos(conteos: List<ConteoFisicoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarConteo(conteo: ConteoFisicoEntity)
}
