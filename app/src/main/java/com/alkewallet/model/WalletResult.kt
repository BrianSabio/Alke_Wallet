package com.alkewallet.model

sealed class WalletResult {
    data class Success(val message: String) : WalletResult()
    data class Error(val message: String) : WalletResult()
}
