package com.alkewallet.model

import org.junit.Test
import org.junit.Assert.*

class AuthModelTest {

    @Test
    fun `register new user returns success`() {
        val user = User("Juan", "Perez", "juan.test1@alke.com", "pass123")
        val result = AuthModel.register(user)
        assertTrue(result is WalletResult.Success)
    }

    @Test
    fun `register duplicate email returns error`() {
        val user = User("Ana", "Lopez", "ana.test2@alke.com", "pass123")
        AuthModel.register(user)
        val result = AuthModel.register(user.copy(firstName = "Ana2"))
        assertTrue(result is WalletResult.Error)
    }

    @Test
    fun `login with correct credentials returns success and sets currentUser`() {
        val user = User("Carlos", "Diaz", "carlos.test3@alke.com", "pass456")
        AuthModel.register(user)
        val result = AuthModel.login("carlos.test3@alke.com", "pass456")
        assertTrue(result is WalletResult.Success)
        assertEquals("carlos.test3@alke.com", AuthModel.currentUser?.email)
    }

    @Test
    fun `login with wrong password returns error`() {
        val user = User("Marta", "Ruiz", "marta.test4@alke.com", "pass789")
        AuthModel.register(user)
        val result = AuthModel.login("marta.test4@alke.com", "wrongpass")
        assertTrue(result is WalletResult.Error)
    }

    @Test
    fun `logout clears currentUser`() {
        val user = User("Luis", "Nova", "luis.test5@alke.com", "pass000")
        AuthModel.register(user)
        AuthModel.login("luis.test5@alke.com", "pass000")
        AuthModel.logout()
        assertNull(AuthModel.currentUser)
    }

    @Test
    fun `register with invalid email format returns error`() {
        val user = User("Test", "User", "correo-invalido-sin-arroba", "pass123")
        val result = AuthModel.register(user)
        assertTrue(result is WalletResult.Error)
    }

    @Test
    fun `login with invalid email format returns error`() {
        val result = AuthModel.login("no-es-un-email", "cualquierpass")
        assertTrue(result is WalletResult.Error)
    }
}
