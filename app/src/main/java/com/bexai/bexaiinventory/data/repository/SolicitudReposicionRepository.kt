package com.bexai.bexaiinventory.data.repository

import com.bexai.bexaiinventory.data.local.dao.AlertaDao
import com.bexai.bexaiinventory.data.local.dao.SolicitudReposicionDao
import com.bexai.bexaiinventory.data.local.entity.AlertaEntity
import com.bexai.bexaiinventory.data.local.entity.SolicitudReposicionEntity
import com.bexai.bexaiinventory.data.local.seed.MockData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SolicitudReposicionRepository @Inject constructor(
    private val solicitudReposicionDao: SolicitudReposicionDao,
    private val alertaDao: AlertaDao
) {
    suspend fun seedSolicitudes() {
        if (solicitudReposicionDao.contarSolicitudes() == 0) {
            solicitudReposicionDao.insertarSolicitudes(MockData.solicitudesIniciales)
        }
    }

    fun obtenerSolicitudes(sucursalId: Int = 1): Flow<List<SolicitudReposicionEntity>> {
        return solicitudReposicionDao.obtenerSolicitudes(sucursalId)
    }

    suspend fun emitirSolicitud(solicitud: SolicitudReposicionEntity) {
        solicitudReposicionDao.insertarSolicitud(solicitud)
    }

    suspend fun emitirAlertaManual(alerta: AlertaEntity) {
        alertaDao.insertarAlertas(listOf(alerta))
    }
}
