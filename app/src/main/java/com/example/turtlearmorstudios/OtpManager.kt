package com.example.turtlearmorstudios

import java.security.SecureRandom

class OtpManager {
    private val secureRandom = SecureRandom()
    var currentOtp: String? = null  // Store the current OTP

    fun generateTotp(): String {
        val lowerBound = 100000
        val upperBound = 999999
        val otp = lowerBound + secureRandom.nextInt(upperBound - lowerBound + 1)
        currentOtp = otp.toString()
        return currentOtp!!
    }

    fun verifyTotp(inputOtp: String): Boolean {
        return inputOtp == currentOtp
    }
}