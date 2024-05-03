package com.example.turtlearmorstudios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OtpManagerActivity : AppCompatActivity() {
    private lateinit var otpManager: OtpManager
    private lateinit var editTextOtp: EditText
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_manager)

        otpManager = OtpManager()
        editTextOtp = findViewById<EditText>(R.id.editTextOtp)
        textViewResult = findViewById<TextView>(R.id.textViewResult)
        val buttonGenerateOtp = findViewById<Button>(R.id.buttonGenerateOtp)
        val buttonVerifyOtp = findViewById<Button>(R.id.buttonVerifyOtp)

        buttonGenerateOtp.setOnClickListener {
            val otp = otpManager.generateTotp()
            editTextOtp.setText(otp)
            Toast.makeText(this, "OTP Generated", Toast.LENGTH_SHORT).show()
        }

        buttonVerifyOtp.setOnClickListener {
            val otp = editTextOtp.text.toString()
            val isValid = otpManager.verifyTotp(otp)
            if (isValid) {
                textViewResult.text = "OTP Verified Successfully!"
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                textViewResult.text = "Invalid OTP."
            }
        }
    }
}