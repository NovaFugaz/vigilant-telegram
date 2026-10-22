package com.bexai.bexainventory.model

data class LoginModel(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val esValido: Boolean = false
)