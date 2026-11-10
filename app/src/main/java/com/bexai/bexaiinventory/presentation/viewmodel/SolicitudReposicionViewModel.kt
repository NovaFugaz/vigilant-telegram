package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.local.entity.AlertaEntity
import com.bexai.bexaiinventory.data.local.entity.SolicitudReposicionEntity
import com.bexai.bexaiinventory.data.repository.ProductoRepository
import com.bexai.bexaiinventory.data.repository.SolicitudReposicionRepository
import com.bexai.bexaiinventory.domain.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class SolicitudReposicionUiState(
    val solicitudes: List<SolicitudReposicionEntity> = emptyList(),
    val productosBajoMinimo: List<Producto> = emptyList(),
    val todosLosProductos: List<Producto> = emptyList(),
    val cargando: Boolean = true,
    val mensajeExito: String? = null
)

class SolicitudReposicionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDataBase.getDatabase(application)
    private val repository = SolicitudReposicionRepository(
        db.solicitudReposicionDao(),
        db.alertaDao()
    )
    private val productoRepository = ProductoRepository(db.productoDao())

    private val _uiState = MutableStateFlow(SolicitudReposicionUiState())
    val uiState: StateFlow<SolicitudReposicionUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            repository.seedSolicitudes()
            productoRepository.seedProductos()

            // Escuchar solicitudes
            launch {
                repository.obtenerSolicitudes(sucursalId = 1).collect { lista ->
                    _uiState.update { it.copy(solicitudes = lista, cargando = false) }
                }
            }

            // Escuchar productos bajo mínimo
            launch {
                productoRepository.obtenerProductos(sucursalId = 1).collect { productos ->
                    val bajoMinimo = productos.filter { it.stockTienda <= it.stockMinimo }
                    _uiState.update {
                        it.copy(
                            todosLosProductos = productos,
                            productosBajoMinimo = bajoMinimo
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun emitirSolicitud(
        sku: String,
        nombreProducto: String,
        cantidad: Int,
        destino: String,
        prioridad: String,
        observacion: String
    ) {
        viewModelScope.launch {
            val ahora = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val fechaIso = ahora.date.toString()

            val solicitud = SolicitudReposicionEntity(
                sku = sku,
                nombreProducto = nombreProducto,
                cantidad = cantidad,
                destino = destino,
                prioridad = prioridad,
                observacion = observacion,
                estado = "Pendiente",
                fechaIso = fechaIso,
                sucursalId = 1
            )
            repository.emitirSolicitud(solicitud)
            _uiState.update { it.copy(mensajeExito = "Solicitud de reposición registrada para $sku") }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun emitirAlertaQuiebreInminente(
        sku: String,
        nombreProducto: String,
        stockActual: Int,
        stockRequerido: Int,
        ubicacion: String
    ) {
        viewModelScope.launch {
            val ahora = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val fechaIso = ahora.date.toString()
            val horaIso = "${ahora.hour.toString().padStart(2, '0')}:${ahora.minute.toString().padStart(2, '0')}:${ahora.second.toString().padStart(2, '0')}"

            val alerta = AlertaEntity(
                id = "ALT-MANUAL-${System.currentTimeMillis()}",
                tituloAlerta = if (stockActual == 0) "QUIEBRE ACTIVO DECLARADO" else "STOCK CRÍTICO INMINENTE",
                sku = sku,
                ean13 = "",
                nombreProducto = nombreProducto,
                stockActual = stockActual,
                stockRequerido = stockRequerido,
                ubicacionNombre = ubicacion,
                severidadAlerta = if (stockActual == 0) "QUIEBRE_CRITICO" else "STOCK_CRITICO_INMINENTE",
                fechaIso = fechaIso,
                horaIso = horaIso,
                atendida = false
            )

            repository.emitirAlertaManual(alerta)
            _uiState.update { it.copy(mensajeExito = "Alerta de quiebre inminente emitida a gerencia para $nombreProducto") }
        }
    }

    fun limpiarMensaje() {
        _uiState.update { it.copy(mensajeExito = null) }
    }
}
