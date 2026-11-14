package com.bexai.bexaiinventory.presentation.viewmodel

import android.app.Application
import app.cash.turbine.test
import com.bexai.bexaiinventory.domain.repository.OperadorRepository
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
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application
    private lateinit var operadorRepository: OperadorRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        application = mockk(relaxed = true)
        operadorRepository = mockk(relaxed = true)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateHasEmptyCodeAndPin() = runTest {
        val viewModel = LoginViewModel(application, operadorRepository)

        viewModel.uiState.test {
            val state = awaitItem()
            state.codigo shouldBe ""
            state.pin shouldBe ""
            state.codigoError shouldBe null
            state.pinError shouldBe null
            state.cargando shouldBe false
        }
    }

    @Test
    fun onCodigoChangeUpdatesCodeAndTrims() = runTest {
        val viewModel = LoginViewModel(application, operadorRepository)

        viewModel.uiState.test {
            awaitItem() // initial

            viewModel.onCodigoChange("  op001 ")
            val updated = awaitItem()
            updated.codigo shouldBe "OP001"
            updated.codigoError shouldBe null
        }
    }

    @Test
    fun onPinChangeFiltersNonDigitsAndLimitsTo6() = runTest {
        val viewModel = LoginViewModel(application, operadorRepository)

        viewModel.uiState.test {
            awaitItem() // initial

            viewModel.onPinChange("123abc456789")
            val updated = awaitItem()
            updated.pin shouldBe "123456"
            updated.pinError shouldBe null
        }
    }

    @Test
    fun validarFailsWhenCodeIsBlank() = runTest {
        val viewModel = LoginViewModel(application, operadorRepository)

        viewModel.uiState.test {
            awaitItem() // initial

            viewModel.validar()
            val updated = awaitItem()
            updated.codigoError shouldBe "Ingrese su código de operador"
        }
    }

    @Test
    fun validarFailsWhenPinLengthIsNot6() = runTest {
        val viewModel = LoginViewModel(application, operadorRepository)

        viewModel.uiState.test {
            awaitItem() // initial

            viewModel.onCodigoChange("OP001")
            awaitItem()

            viewModel.onPinChange("123")
            awaitItem()

            viewModel.validar()
            val updated = awaitItem()
            updated.pinError shouldBe "El PIN debe tener 6 dígitos"
        }
    }
}
