package com.bexai.bexaiinventory.data.repository

import com.bexai.bexaiinventory.data.local.dao.ConteoFisicoDao
import com.bexai.bexaiinventory.data.local.dao.DiferenciaStockInfo
import com.bexai.bexaiinventory.data.local.entity.ConteoFisicoEntity
import com.bexai.bexaiinventory.data.local.seed.MockData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiferenciasRepository @Inject constructor(
    private val conteoFisicoDao: ConteoFisicoDao
) {
    suspend fun seedConteos() {
        if (conteoFisicoDao.contarConteos() == 0) {
            conteoFisicoDao.insertarConteos(MockData.conteosIniciales)
        }
    }

    fun obtenerDescalces(sucursalId: Int = 1): Flow<List<DiferenciaStockInfo>> {
        return conteoFisicoDao.obtenerDescalces(sucursalId)
    }

    suspend fun registrarConteo(conteo: ConteoFisicoEntity) {
        conteoFisicoDao.insertarConteo(conteo)
    }
}
