package com.example.turtlearmorstudios

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.example.turtlearmorstudios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var passwordsAdapter: PasswordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val passwords = mutableListOf(
            PasswordInfo("Account Name", "user@example.com", "Password123"),
        )
        passwordsAdapter = PasswordsAdapter(passwords,
            onEdit = { passwordInfo, position ->
                editPassword(passwordInfo, position)
            },
            onDelete = { position ->
                passwordsAdapter.removePassword(position)
                Toast.makeText(this, "Deleted password at position: $position", Toast.LENGTH_SHORT).show()
            }
        )
        setupRecyclerView()
        setupAddPasswordButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewPasswords.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = passwordsAdapter
        }
    }

    private fun setupAddPasswordButton() {
        binding.btnAddPassword.setOnClickListener {
            addNewPassword()
        }
    }

    private fun addNewPassword() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_password, null)
        val editTextServiceName = dialogView.findViewById<EditText>(R.id.editTextServiceName)
        val editTextUsername = dialogView.findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextNewPassword)

        AlertDialog.Builder(this)
            .setTitle("Add New Password")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val serviceName = editTextServiceName.text.toString()
                val username = editTextUsername.text.toString()
                val password = editTextPassword.text.toString()
                if (serviceName.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                    val passwordInfo = PasswordInfo(serviceName, username, password)
                    passwordsAdapter.addPassword(passwordInfo)
                    Toast.makeText(this, "Password added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun editPassword(passwordInfo: PasswordInfo, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_password, null)
        val editTextServiceName = dialogView.findViewById<EditText>(R.id.editTextServiceName)
        val editTextUsername = dialogView.findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextPassword)

        editTextServiceName.setText(passwordInfo.serviceName)
        editTextUsername.setText(passwordInfo.username)
        editTextPassword.setText(passwordInfo.password)

        AlertDialog.Builder(this)
            .setTitle("Edit Password")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newServiceName = editTextServiceName.text.toString()
                val newUsername = editTextUsername.text.toString()
                val newPassword = editTextPassword.text.toString()
                if (newServiceName.isNotEmpty() && newUsername.isNotEmpty() && newPassword.isNotEmpty()) {
                    val newInfo = PasswordInfo(newServiceName, newUsername, newPassword)
                    passwordsAdapter.updatePassword(position, newInfo)
                    Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
