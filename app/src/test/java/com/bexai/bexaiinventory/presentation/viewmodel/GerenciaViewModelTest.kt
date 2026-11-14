package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GerenciaViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        application = mockk(relaxed = true)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateLoadsSummaryCorrectly() = runTest {
        val viewModel = GerenciaViewModel(application)

        viewModel.uiState.test {
            val initialState = awaitItem()
            initialState.selectedFiltroTiempo shouldBe "Hoy (Vivo)"
            initialState.sucursalesDisponibles.isEmpty() shouldBe false
        }
    }

    @Test
    fun onFiltroTiempoChangeUpdatesFilterStateCorrectly() = runTest {
        val viewModel = GerenciaViewModel(application)

        viewModel.uiState.test {
            val initialState = awaitItem()
            initialState.selectedFiltroTiempo shouldBe "Hoy (Vivo)"

            viewModel.onFiltroTiempoChange("Semana")

            val updatedState = awaitItem()
            updatedState.selectedFiltroTiempo shouldBe "Semana"
        }
    }

    @Test
    fun onSucursalChangeUpdatesSelectedSucursalCorrectly() = runTest {
        val viewModel = GerenciaViewModel(application)

        viewModel.uiState.test {
            val initialState = awaitItem()
            initialState.selectedSucursalNombre shouldBe "Todas las Sucursales (4 activas)"

            viewModel.onSucursalChange("Sucursal Norte")

            val updatedState = awaitItem()
            updatedState.selectedSucursalNombre shouldBe "Sucursal Norte"
        }
    }
}
