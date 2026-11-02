package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.domain.model.Rol
import com.bexai.bexaiinventory.domain.model.Turno
import com.bexai.bexaiinventory.domain.repository.JornadaRepository
import com.bexai.bexaiinventory.domain.repository.OperadorRepository
import kotlinx.coroutines.launch

// Es el selector de Turno? Deberíamos llamarlo SeleccionTurnoViewModel (netamente para que no haya atado con otros selectores)

// ando diciendo puras weas, si es turno y lo voy a cambiar twin
// al final el operador tiene un trabajo asignado y realiza el inicio de la jornada laboral, con el turno correspondiente
data class AsignacionUi(
    val sucursalId: Int,
    val sucursalNombre: String,
    val direccion: String,
    val turno: Turno,
    val rol: Rol
)

class SeleccionViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDataBase.getDatabase(application)
    private val jornadaRepo = JornadaRepository(db.sucursalDao(), db.jornadaDao())
    private val operadorRepo = OperadorRepository(db.operadorDao())

    var asignacion by mutableStateOf<AsignacionUi?>(null)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    val puedeComenzar: Boolean get() = asignacion != null

    fun cargar(operadorCodigo: String) {
        viewModelScope.launch {
            jornadaRepo.datitosSurcursal()
            val operador = operadorRepo.find(operadorCodigo)
            val sucursal = operador?.let { jornadaRepo.sucursalPorId(it.sucursalId) }

            if (operador == null || sucursal == null) {
                error = "No tienes un centro de trabajo asignado. Contacta a tu supervisor."
                return@launch
            }
            asignacion = AsignacionUi(
                sucursalId = sucursal.id,
                sucursalNombre = sucursal.nombre,
                direccion = sucursal.direccion,
                turno = operador.turno,
                rol = operador.rol
            )
        }
    }

    fun comenzar(operadorCodigo: String, onListo: (Rol) -> Unit) {
        val a = asignacion ?: return
        viewModelScope.launch {
            jornadaRepo.iniciar(operadorCodigo, a.sucursalId, a.turno)
            onListo(a.rol)
        }
    }
}