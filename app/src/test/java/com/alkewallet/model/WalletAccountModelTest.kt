package com.alkewallet.model

import org.junit.Test
import org.junit.Assert.*

class WalletAccountModelTest {

    @Test
    fun `sendMoney with valid amount decreases balance and adds transaction`() {
        val balanceBefore = WalletAccountModel.balance
        val result = WalletAccountModel.sendMoney("TestUserA", 50.0, "2025-01-01")
        assertTrue(result is WalletResult.Success)
        assertEquals(balanceBefore - 50.0, WalletAccountModel.balance, 0.001)
        assertTrue(WalletAccountModel.getTransactions().first().isSent)
    }

    @Test
    fun `sendMoney with amount greater than balance returns error and does not change balance`() {
        val balanceBefore = WalletAccountModel.balance
        val result = WalletAccountModel.sendMoney("TestUserB", balanceBefore + 1000.0, "2025-01-02")
        assertTrue(result is WalletResult.Error)
        assertEquals(balanceBefore, WalletAccountModel.balance, 0.001)
    }

    @Test
    fun `sendMoney with zero or negative amount returns error`() {
        val result = WalletAccountModel.sendMoney("TestUserC", 0.0, "2025-01-03")
        assertTrue(result is WalletResult.Error)

        val resultNegative = WalletAccountModel.sendMoney("TestUserD", -10.0, "2025-01-03")
        assertTrue(resultNegative is WalletResult.Error)
    }

    @Test
    fun `requestMoney with valid amount increases balance and adds transaction`() {
        val balanceBefore = WalletAccountModel.balance
        val result = WalletAccountModel.requestMoney("TestUserE", 75.0, "2025-01-04")
        assertTrue(result is WalletResult.Success)
        assertEquals(balanceBefore + 75.0, WalletAccountModel.balance, 0.001)
        assertFalse(WalletAccountModel.getTransactions().first().isSent)
    }

    @Test
    fun `requestMoney with zero or negative amount returns error`() {
        val result = WalletAccountModel.requestMoney("TestUserF", 0.0, "2025-01-05")
        assertTrue(result is WalletResult.Error)
    }

    @Test
    fun `requestMoney with amount exceeding max limit returns error`() {
        val result = WalletAccountModel.requestMoney("TestUserG", 5000.01, "2025-01-06")
        assertTrue(result is WalletResult.Error)
    }

    @Test
    fun `requestMoney with amount exactly at max limit returns success`() {
        val result = WalletAccountModel.requestMoney("TestUserH", 5000.0, "2025-01-07")
        assertTrue(result is WalletResult.Success)
    }
}
