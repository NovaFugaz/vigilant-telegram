package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.local.entity.Sucursal
import com.bexai.bexaiinventory.domain.model.Turno
import com.bexai.bexaiinventory.domain.repository.JornadaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SeleccionViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDataBase.getDatabase(application)
    private val repository = JornadaRepository(db.sucursalDao(), db.jornadaDao())

    val sucursales: StateFlow<List<Sucursal>> = repository.sucursal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var sucursalId by mutableStateOf<Int?>(null)
        private set
    var turno by mutableStateOf<Turno?>(null)
        private set

    val puedeComenzar: Boolean get() = sucursalId != null && turno != null

    init {
        viewModelScope.launch { repository.datitosSurcursal() }
    }

    fun precarga(operadorCodigo: String) {
        viewModelScope.launch {
            val previa = repository.jornadaGuardada(operadorCodigo)
            if (previa != null && sucursalId == null) {
                sucursalId = previa.sucursalId
                turno = previa.turno
            }
        }
    }

    fun elegirSucursal(id: Int) { sucursalId = id }
    fun elegirTurno(t: Turno) { turno = t }

    fun comenzar(operadorCodigo: String, onListo: () -> Unit) {
        val s = sucursalId ?: return
        val t = turno ?: return
        viewModelScope.launch {
            repository.iniciar(operadorCodigo, s, t)
            onListo()
        }
    }
}