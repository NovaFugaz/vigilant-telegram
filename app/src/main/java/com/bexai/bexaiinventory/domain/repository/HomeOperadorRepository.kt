package com.bexai.bexaiinventory.domain.repository

import com.bexai.bexaiinventory.data.local.dao.MetricasJornadaDao
import com.bexai.bexaiinventory.data.local.dao.TareaDao
import com.bexai.bexaiinventory.data.local.seed.MockData
import com.bexai.bexaiinventory.data.mapper.toDomain
import com.bexai.bexaiinventory.domain.model.MetricasJornada
import com.bexai.bexaiinventory.domain.model.Tarea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeOperadorRepository(
    private val metricasDao: MetricasJornadaDao,
    private val tareaDao: TareaDao
) {

    fun metricas(codigo: String): Flow<MetricasJornada> {
        return metricasDao.obtener(codigo).map { entity ->
            entity?.toDomain() ?: MetricasJornada()
        }
    }

    // Escucha las tareas usando el obtenerPorOperador
    fun tareas(codigo: String): Flow<List<Tarea>> {
        return tareaDao.obtenerPorOperador(codigo).map { lista ->
            lista.map { it.toDomain() }
        }
    }

    // Poblar la base de datos de manera limpia usando 'contar'
    suspend fun datosP(codigo: String) {
        // Usamos la función suspendida del DAO. Si retorna 0, significa que está vacía (obvio no).
        val cantidadTareas = tareaDao.contar(codigo)

        if (cantidadTareas == 0) {
            // Insertar Métricas desde MockData
            val metricasSeed = MockData.metricasIniciales(codigo)
            metricasDao.guardar(metricasSeed)

            // Insertar Tareas desde MockData
            val tareasSeed = MockData.tareasIniciales(codigo)
            if (tareasSeed.isNotEmpty()) {
                tareaDao.insertar(tareasSeed) // Usamos 'insertar' que recibe la lista
            }
        }
    }
}
