package com.bexai.bexaiinventory.data.repository

import com.bexai.bexaiinventory.data.local.dao.AlertaDao
import com.bexai.bexaiinventory.data.local.dao.ConteoFisicoDao
import com.bexai.bexaiinventory.data.local.dao.ProductoDao
import com.bexai.bexaiinventory.data.local.dao.SucursalDao
import com.bexai.bexaiinventory.data.local.seed.MockData
import com.bexai.bexaiinventory.domain.model.BalanceAnioVsMes
import com.bexai.bexaiinventory.domain.model.CapacidadCentro
import com.bexai.bexaiinventory.domain.model.ReporteGerencialSummary
import com.bexai.bexaiinventory.domain.model.ReporteItemTienda
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GerenciaRepository(
    private val productoDao: ProductoDao? = null,
    private val conteoFisicoDao: ConteoFisicoDao? = null,
    private val alertaDao: AlertaDao? = null,
    private val sucursalDao: SucursalDao? = null
) {

    fun obtenerSummaryGerencial(): Flow<ReporteGerencialSummary> = flow {
        emit(MockData.summaryGerencialInicial)
    }

    fun obtenerCapacidadCentros(): Flow<List<CapacidadCentro>> = flow {
        emit(MockData.capacidadesCentrosIniciales)
    }

    fun obtenerReporteTiendas(): Flow<List<ReporteItemTienda>> = flow {
        emit(MockData.reporteTiendasIniciales)
    }

    fun obtenerBalanceAnioVsMes(): Flow<BalanceAnioVsMes> = flow {
        emit(MockData.balanceAnioVsMesInicial)
    }
}
