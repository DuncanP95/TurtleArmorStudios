package com.example.turtlearmorstudios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Registers a new user
    fun registerUser(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Attempt to add the user to the repository
                val isAdded = userRepository.addUser(username, password)
                if (isAdded) {
                    onSuccess()
                } else {
                    onError("Failed to register user.")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }

    // Authenticates an existing user
    fun authenticateUser(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userRepository.authenticate(username, password)
                if (user != null) {
                    onSuccess()
                } else {
                    onError("Authentication failed.")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }
}