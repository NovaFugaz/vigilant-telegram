package com.bexai.bexaiinventory.domain.repository

import com.bexai.bexaiinventory.data.local.dao.OperadorDao
import com.bexai.bexaiinventory.data.local.entity.Operador
import com.bexai.bexaiinventory.data.local.seed.MockData
import com.bexai.bexaiinventory.domain.model.Rol
import com.bexai.bexaiinventory.domain.model.Turno
import javax.inject.Inject

class OperadorRepository (private val dao: OperadorDao) {


    // agregue estos de prueba
    // aunque podriamos agregar mas
    suspend fun datitos() {
        if (dao.contar() == 0) {
            MockData.operadoresIniciales.forEach { dao.agregarOperador(it) }
        }
    }

    suspend fun find(codigo: String): Operador? = dao.findByCode(codigo)
}