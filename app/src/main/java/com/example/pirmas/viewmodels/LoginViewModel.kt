package com.example.pirmas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pirmas.ApiService
import com.example.pirmas.data.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val apiService = ApiService.create()

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(_username.value, _password.value))
                if (response.isSuccessful && response.body()?.success == true) {
                    onLoginSuccess()
                } else if (response.code() == 401) {
                    _loginError.value = "Prisijungimo vardas arba slaptažodis nesutampa"
                } else {
                    _loginError.value = response.body()?.message ?: "Įvyko klaida"
                }
            } catch (e: Exception) {
                _loginError.value = "Įvyko klaida: ${e.message}"
            }
        }
    }
}
