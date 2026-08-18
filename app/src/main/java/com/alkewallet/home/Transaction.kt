package com.alkewallet.home

import java.util.Locale
import kotlin.math.abs

data class Transaction(
    val userName: String,
    val date: String,
    val amount: Double,
    val isSent: Boolean
) {
    fun formattedAmount(): String {
        val sign = if (isSent) "-" else "+"
        return "$sign$${String.format(Locale.US, "%.2f", abs(amount))}"
    }
}