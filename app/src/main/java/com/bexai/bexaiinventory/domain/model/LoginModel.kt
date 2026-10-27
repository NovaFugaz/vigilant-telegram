package com.bexai.bexaiinventory.domain.model
data class LoginModel(
    val codigo: String = "",
    val pin: String = "",
    val rol: Rol = Rol.TERRENO,
    val codigoError: String?= null,
    val pinError: String?=null,
    val rolError: String? = null,
    val loginExitoso: Boolean = false
)