package com.alkewallet.model

object AuthModel {
    private val registeredUsers = mutableListOf<User>()
    var currentUser: User? = null
        private set

    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private fun isValidEmail(email: String): Boolean = emailRegex.matches(email)

    fun register(user: User): WalletResult {
        if (!isValidEmail(user.email)) {
            return WalletResult.Error("Formato de correo inválido")
        }
        if (registeredUsers.any { it.email.equals(user.email, ignoreCase = true) }) {
            return WalletResult.Error("El correo ya está registrado")
        }
        registeredUsers.add(user)
        return WalletResult.Success("Usuario registrado correctamente")
    }

    fun login(email: String, password: String): WalletResult {
        if (!isValidEmail(email)) {
            return WalletResult.Error("Formato de correo inválido")
        }
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
