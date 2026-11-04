package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.domain.model.MetricasJornada
import com.bexai.bexaiinventory.domain.model.Tarea
import com.bexai.bexaiinventory.domain.repository.HomeOperadorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeOperadorUiState(
    val metricas: MetricasJornada = MetricasJornada(),
    val tareas: List<Tarea> = emptyList()
)

class HomeOperadorViewModel(
    application: Application,
    private val codigo: String
) : AndroidViewModel(application) {

    private val db = AppDataBase.getDatabase(application)
    private val repository = HomeOperadorRepository(db.metricasJornadaDao(), db.tareaDao())

    val uiState: StateFlow<HomeOperadorUiState> = combine(
        repository.metricas(codigo),
        repository.tareas(codigo)
    ) { metricas, tareas ->
        HomeOperadorUiState(metricas = metricas, tareas = tareas)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeOperadorUiState())

    init {
        // datos de prueba, solo se crean la primera vez
        viewModelScope.launch { repository.datosP(codigo) }
    }

    companion object {
        // el ViewModel necesita el código del operador, por eso la factory
        fun factory(codigo: String) = viewModelFactory {
            initializer {
                HomeOperadorViewModel(this[APPLICATION_KEY] as Application, codigo)
            }
        }
    }
}