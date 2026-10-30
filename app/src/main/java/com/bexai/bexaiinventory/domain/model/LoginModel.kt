package com.bexai.bexaiinventory.domain.model

import com.bexai.bexaiinventory.data.local.entity.Operador

data class LoginModel(
    val codigo: String = "",
    val pin: String = "",
    val rol: Rol = Rol.TERRENO,
    val codigoError: String?= null,
    val pinError: String?=null,
    val cargando: Boolean = false,
    val operador: Operador?=null // si no es null, el login es correcto
)