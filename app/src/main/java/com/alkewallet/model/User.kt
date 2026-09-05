package com.alkewallet.model

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
) {
    fun fullName(): String = "$firstName $lastName"
}
