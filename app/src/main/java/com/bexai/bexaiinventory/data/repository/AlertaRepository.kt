package com.bexai.bexaiinventory.data.repository

import com.bexai.bexaiinventory.data.local.dao.AlertaDao
import com.bexai.bexaiinventory.data.local.seed.MockData
import com.bexai.bexaiinventory.domain.model.Alerta
import com.bexai.bexaiinventory.domain.model.SeveridadAlerta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class AlertaRepository @Inject constructor(
    private val alertaDao: AlertaDao
) {
    suspend fun seedAlertas() {
        if (alertaDao.contarAlertas() == 0) {
            alertaDao.insertarAlertas(MockData.alertasIniciales)
        }
    }

    fun obtenerAlertas(): Flow<List<Alerta>> {
        return alertaDao.obtenerTodasLasAlertas().map { entities ->
            entities.map { entity ->
                Alerta(
                    id = entity.id,
                    tituloAlerta = entity.tituloAlerta,
                    sku = entity.sku,
                    ean13 = entity.ean13,
                    nombreProducto = entity.nombreProducto,
                    stockActual = entity.stockActual,
                    stockRequerido = entity.stockRequerido,
                    ubicacionNombre = entity.ubicacionNombre,
                    severidad = try { SeveridadAlerta.valueOf(entity.severidadAlerta) } catch (e: Exception) { SeveridadAlerta.ADVERTENCIA_PREVENTIVA },
                    fecha = LocalDate.parse(entity.fechaIso),
                    hora = LocalTime.parse(entity.horaIso),
                    atendida = entity.atendida
                )
            }
        }
    }

    suspend fun atenderAlerta(alertaId: String) {
        alertaDao.marcarComoAtendida(alertaId)
    }
}