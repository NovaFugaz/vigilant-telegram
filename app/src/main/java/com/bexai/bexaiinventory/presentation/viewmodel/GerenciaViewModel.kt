package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.repository.GerenciaRepository
import com.bexai.bexaiinventory.data.local.seed.MockData
import com.bexai.bexaiinventory.domain.model.BalanceAnioVsMes
import com.bexai.bexaiinventory.domain.model.CapacidadCentro
import com.bexai.bexaiinventory.domain.model.ReporteGerencialSummary
import com.bexai.bexaiinventory.domain.model.ReporteItemTienda
import com.bexai.bexaiinventory.util.GerenciaPdfExporter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

data class GerenciaUiState(
    val selectedFiltroTiempo: String = "Hoy (Vivo)", // "Hoy (Vivo)", "Semana", "Mes"
    val selectedSucursalNombre: String = "Todas las Sucursales (4 activas)",
    val summary: ReporteGerencialSummary = MockData.summaryGerencialInicial,
    val capacidades: List<CapacidadCentro> = MockData.capacidadesCentrosIniciales,
    val tiendasReportes: List<ReporteItemTienda> = MockData.reporteTiendasIniciales,
    val balance: BalanceAnioVsMes = MockData.balanceAnioVsMesInicial,
    val cargando: Boolean = true,
    val pdfGenerado: File? = null,
    val mensajeNotificacion: String? = null
)

class GerenciaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GerenciaRepository()

    private val _uiState = MutableStateFlow(GerenciaUiState())
    val uiState: StateFlow<GerenciaUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }

            repository.obtenerSummaryGerencial().collect { summary ->
                _uiState.update { it.copy(summary = summary) }
            }
        }

        viewModelScope.launch {
            repository.obtenerCapacidadCentros().collect { capacidades ->
                _uiState.update { it.copy(capacidades = capacidades) }
            }
        }

        viewModelScope.launch {
            repository.obtenerReporteTiendas().collect { tiendas ->
                _uiState.update { it.copy(tiendasReportes = tiendas, cargando = false) }
            }
        }

        viewModelScope.launch {
            repository.obtenerBalanceAnioVsMes().collect { balance ->
                _uiState.update { it.copy(balance = balance) }
            }
        }
    }

    fun onFiltroTiempoChange(filtro: String) {
        _uiState.update { it.copy(selectedFiltroTiempo = filtro) }
    }

    fun onSucursalChange(sucursalNombre: String) {
        _uiState.update { it.copy(selectedSucursalNombre = sucursalNombre) }
    }

    fun exportarTableroPdf(context: Context) {
        val state = uiState.value
        val file = GerenciaPdfExporter.exportarReporteGerencial(
            context = context,
            tituloReporte = "Tablero Gerencial - ${state.selectedFiltroTiempo} (${state.selectedSucursalNombre})",
            summary = state.summary,
            tiendas = state.tiendasReportes,
            capacidades = state.capacidades,
            balance = state.balance
        )

        if (file != null) {
            _uiState.update {
                it.copy(
                    pdfGenerado = file,
                    mensajeNotificacion = "PDF exportado correctamente."
                )
            }
            GerenciaPdfExporter.abrirPdf(context, file)
        } else {
            _uiState.update {
                it.copy(mensajeNotificacion = "Error al generar el archivo PDF.")
            }
        }
    }

    fun limpiarNotificacion() {
        _uiState.update { it.copy(mensajeNotificacion = null) }
    }
}
