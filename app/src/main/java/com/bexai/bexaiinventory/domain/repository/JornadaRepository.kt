package com.bexai.bexaiinventory.domain.repository

import com.bexai.bexaiinventory.data.local.dao.JornadaDao
import com.bexai.bexaiinventory.data.local.dao.SucursalDao
import com.bexai.bexaiinventory.data.local.entity.Jornada
import com.bexai.bexaiinventory.data.local.entity.Sucursal
import com.bexai.bexaiinventory.domain.model.Turno

class JornadaRepository(
    private val sucursalDao: SucursalDao,
    private val jornadaDao: JornadaDao
) {
    fun sucursal() = sucursalDao.obtenerTodos()
    fun jornada(codigo: String) = jornadaDao.obtenerJornadaActual(codigo)

    suspend fun jornadaGuardada(codigo: String): Jornada? = jornadaDao.obtenerJornada(codigo)

    suspend fun iniciar(operadorCodigo: String, sucursalId: Int, turno: Turno) =
        jornadaDao.guardarJornada(Jornada(operadorCodigo, sucursalId, turno))

    // datos de prueba
    suspend fun datitosSurcursal() {
        if (sucursalDao.contarSucursal() == 0) {
            sucursalDao.agregarSucursal(
                listOf(
                    Sucursal(1, "Bodega Central Pudahuel", "Sector Industrial Poniente"),
                    Sucursal(2, "Sucursal Santiago Centro", "Av. L.B. O'Higgins"),
                    Sucursal(3, "Sucursal Viña del Mar", "Mall Fondito-Marino Poniente"),
                    Sucursal(4, "Sucursal Concepción", "Av. ejemplo123")
                )
            )
        }
    }
}