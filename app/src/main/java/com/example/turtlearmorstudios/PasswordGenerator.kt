package com.example.turtlearmorstudios

import java.security.SecureRandom

object PasswordGenerator {

    private const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
    private const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val NUMBERS = "0123456789"
    private const val SYMBOLS = "!@#$%^&*()_+-=[]{}|;:',.<>?~"

    fun generatePassword(length: Int, useNumbers: Boolean, useSymbols: Boolean, useUppercase: Boolean): String {
        var passwordCharacters = LOWERCASE
        if (useNumbers) {
            passwordCharacters += NUMBERS
        }
        if (useSymbols) {
            passwordCharacters += SYMBOLS
        }
        if (useUppercase) {
            passwordCharacters += UPPERCASE
        }

        val random = SecureRandom()
        return (1..length)
            .map { random.nextInt(passwordCharacters.length) }
            .map(passwordCharacters::get)
            .joinToString("")
    }
}