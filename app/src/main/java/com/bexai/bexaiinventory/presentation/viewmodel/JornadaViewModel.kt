package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.local.entity.JornadaDetalle
import com.bexai.bexaiinventory.domain.repository.JornadaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface JornadaEstado {
    data object Cargando : JornadaEstado
    data object SinJornada : JornadaEstado
    data class Iniciada(val detalle: JornadaDetalle) : JornadaEstado
}
class JornadaViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val db = AppDataBase.getDatabase(application)
    private val repository = JornadaRepository(db.sucursalDao(), db.jornadaDao())
    private val codigo: String = savedStateHandle["codigo"] ?: ""

    val estado: StateFlow<JornadaEstado> = repository.jornada(codigo)
        .map { if (it == null) JornadaEstado.SinJornada else JornadaEstado.Iniciada(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JornadaEstado.Cargando)
}