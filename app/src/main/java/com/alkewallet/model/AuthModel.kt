package com.alkewallet.model

object AuthModel {
    private val registeredUsers = mutableListOf<User>()
    var currentUser: User? = null
        private set

    fun register(user: User): WalletResult {
        if (registeredUsers.any { it.email.equals(user.email, ignoreCase = true) }) {
            return WalletResult.Error("El correo ya está registrado")
        }
        registeredUsers.add(user)
        return WalletResult.Success("Usuario registrado correctamente")
    }

    fun login(email: String, password: String): WalletResult {
        val user = registeredUsers.find {
            it.email.equals(email, ignoreCase = true) && it.password == password
        }
        return if (user != null) {
            currentUser = user
            WalletResult.Success("Login exitoso")
        } else {
            WalletResult.Error("Credenciales inválidas")
        }
    }

    fun logout() {
        currentUser = null
    }
}
