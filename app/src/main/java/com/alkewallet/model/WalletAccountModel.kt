package com.alkewallet.model

object WalletAccountModel {
    private const val MAX_REQUEST_AMOUNT = 5000.0

    var balance: Double = 1000.0
        private set

    private val transactions = mutableListOf<Transaction>()

    fun getTransactions(): List<Transaction> = transactions.toList()

    fun sendMoney(toUserName: String, amount: Double, date: String): WalletResult {
        if (amount <= 0) return WalletResult.Error("El monto debe ser mayor a 0")
        if (amount > balance) return WalletResult.Error("Saldo insuficiente")
        balance -= amount
        transactions.add(0, Transaction(toUserName, date, amount, isSent = true))
        return WalletResult.Success("Envío realizado correctamente")
    }

    fun requestMoney(fromUserName: String, amount: Double, date: String): WalletResult {
        if (amount <= 0) return WalletResult.Error("El monto debe ser mayor a 0")
        if (amount > MAX_REQUEST_AMOUNT) return WalletResult.Error("El monto máximo por solicitud es $5000.00")
        balance += amount
        transactions.add(0, Transaction(fromUserName, date, amount, isSent = false))
        return WalletResult.Success("Solicitud/ingreso registrado correctamente")
    }
}
