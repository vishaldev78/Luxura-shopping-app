package com.shops.ecomm.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.shops.ecomm.data.repository.AuthRepository
import com.shops.ecomm.data.repository.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResult?>(null)
    val authState = _authState.asStateFlow()

    fun signInWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            _authState.value = repository.signInWithGoogle(credential)
        }
    }

    fun resetState() {
        _authState.value = null
    }

    fun isUserLoggedIn() = repository.getCurrentUser() != null
}
