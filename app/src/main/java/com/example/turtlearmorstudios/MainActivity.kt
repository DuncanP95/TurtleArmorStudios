package com.example.turtlearmorstudios

import PasswordsAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turtlearmorstudios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var passwordsAdapter: PasswordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val passwords = mutableListOf(
            PasswordInfo("Facebook", "user@example.com", "Password123"),
            PasswordInfo("Myspace", "user@example.com", "Password123"),
            PasswordInfo("Ubisoft", "user@example.com", "Password123"),
            PasswordInfo("World of Warcraft", "user@example.com", "Password123"),
            PasswordInfo("Ubisoft", "user@example.com", "Password123"),
            PasswordInfo("Youtube", "user@example.com", "Password123"),
            PasswordInfo("Unreal Engine", "user@example.com", "Password123"),
            PasswordInfo("Work", "user@example.com", "Password123"),
            PasswordInfo("Amazon", "user@example.com", "Password123"),
            PasswordInfo("This is Why I'm Broke", "user@example.com", "Password123"),
            PasswordInfo("Yahoo", "user@example.com", "Password123"),
            PasswordInfo("Gmail", "user@example.com", "Password123"),
            PasswordInfo("MyMiami", "user@example.com", "Password123"),
            PasswordInfo("BeeKeeper", "user@example.com", "Password123"),
            PasswordInfo("EverQuest", "user@example.com", "Password123"),
            PasswordInfo("Github", "user@example.com", "Password123"),
            PasswordInfo("Twitch", "user@example.com", "Password123"),
            PasswordInfo("Discord", "user@example.com", "Password123"),
            PasswordInfo("Roblox", "user@example.com", "Password123"),
            PasswordInfo("LimeWire", "user@example.com", "Password123"),
            PasswordInfo("Roll20", "user@example.com", "Password123"),
            PasswordInfo("Reddit", "user@example.com", "Password123"),
            PasswordInfo("Nintendo", "user@example.com", "Password123"),
            PasswordInfo("Playstation", "user@example.com", "Password123"),
            PasswordInfo("Microsoft", "user@example.com", "Password123"),
            PasswordInfo("Ancestry", "user@example.com", "Password123"),
            PasswordInfo("Blizzard", "user@example.com", "Password123"),
            PasswordInfo("Etsy", "user@example.com", "Password123"),
            PasswordInfo("Walmart", "user@example.com", "Password123"),
            PasswordInfo("EpicGames", "user@example.com", "Password123"),
            PasswordInfo("Twitter", "user@example.com", "Password123"),
            PasswordInfo("Instagram", "user@example.com", "Password123"),
            PasswordInfo("iTunes", "user@example.com", "Password123"),
            PasswordInfo("iCloud", "user@example.com", "Password123"),
            PasswordInfo("Pinterest", "user@example.com", "Password123"),
            PasswordInfo("Spotify", "user@example.com", "Password123"),
            PasswordInfo("Duke Energy", "user@example.com", "Password123"),
            PasswordInfo("Kroger", "user@example.com", "Password123"),
            PasswordInfo("LinkedIn", "user@example.com", "Password123"),
            PasswordInfo("Zillow", "user@example.com", "Password123"),
            PasswordInfo("BlueStacks", "user@example.com", "Password123"),

            )
        passwordsAdapter = PasswordsAdapter(
            passwords,
            this,
            onEdit = { position ->
                val passwordInfo = passwords[position]
                editPassword(passwordInfo, position)
            },
            onDelete = { position ->
                passwordsAdapter.removePassword(position)
                passwords.removeAt(position)
                passwordsAdapter.notifyItemRemoved(position)
                passwordsAdapter.notifyItemRangeChanged(position, passwords.size)
            }
        )
        setupRecyclerView()
        setupSearchView()
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

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Search", "Query text change: $newText")
                passwordsAdapter.filter(newText ?: "")
                return true
            }
        })
    }

    private fun addNewPassword() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_password, null)
        val editTextServiceName = dialogView.findViewById<EditText>(R.id.editTextServiceName)
        val editTextUsername = dialogView.findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextNewPassword)
        val buttonGeneratePassword = dialogView.findViewById<Button>(R.id.buttonGeneratePassword)
        val seekBarPasswordLength = dialogView.findViewById<SeekBar>(R.id.seekBarPasswordLength)
        val textViewPasswordLength = dialogView.findViewById<TextView>(R.id.textViewPasswordLength)
        val checkBoxNumbers = dialogView.findViewById<CheckBox>(R.id.checkBoxNumbers)
        val checkBoxSymbols = dialogView.findViewById<CheckBox>(R.id.checkBoxSymbols)
        val checkBoxUppercase = dialogView.findViewById<CheckBox>(R.id.checkBoxUppercase)

        seekBarPasswordLength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewPasswordLength.text = "Password Length: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        buttonGeneratePassword.setOnClickListener {
            val length = seekBarPasswordLength.progress
            val useNumbers = checkBoxNumbers.isChecked
            val useSymbols = checkBoxSymbols.isChecked
            val useUppercase = checkBoxUppercase.isChecked
            val generatedPassword =
                PasswordGenerator.generatePassword(length, useNumbers, useSymbols, useUppercase)
            editTextPassword.setText(generatedPassword)
        }

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
