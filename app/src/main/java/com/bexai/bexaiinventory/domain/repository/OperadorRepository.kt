package com.bexai.bexaiinventory.domain.repository

import com.bexai.bexaiinventory.data.local.dao.OperadorDao
import com.bexai.bexaiinventory.data.local.entity.Operador
import com.bexai.bexaiinventory.domain.model.Rol
import javax.inject.Inject

class OperadorRepository (private val dao: OperadorDao) {


    // agregue estos de prueba
    // aunque podriamos agregar mas
    suspend fun datitos() {
        if (dao.contar() == 0) {
            dao.agregarOperador(Operador("OP-1001","Rin Tohsaka", "123456", Rol.TERRENO))
            dao.agregarOperador(Operador("OP-1002","Gilgamesh", "654321", Rol.BODEGA))
            dao.agregarOperador(Operador("OP-1003","Bate galactico", "234567", Rol.GERENCIA))
        }
    }

    suspend fun find(codigo: String): Operador? = dao.findByCode(codigo)
}