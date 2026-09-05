package com.alkewallet.model

import java.util.Locale

data class Transaction(
    val userName: String,
    val date: String,
    val amount: Double,
    val isSent: Boolean
) {
    fun formattedAmount(): String {
        val sign = if (isSent) "-" else "+"
        return String.format(Locale.US, "%s$%.2f", sign, amount)
    }
}
