package com.shops.ecomm.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.shops.ecomm.data.repository.UserRepositoryImpl
import com.shops.ecomm.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        val currentUserId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                val profile = userRepository.getUser(currentUserId)
                if (profile != null) {
                    _userProfile.value = profile
                } else {
                    val defaultUser = User(
                        id = currentUserId,
                        name = "Premium User",
                        email = "user@luxura.com",
                        phoneNumber = auth.currentUser?.phoneNumber ?: ""
                    )
                    userRepository.saveUser(defaultUser)
                    _userProfile.value = defaultUser
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateProfile(name: String, email: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val current = _userProfile.value ?: return@launch
            val updated = current.copy(name = name, email = email)
            userRepository.saveUser(updated)
            _userProfile.value = updated
        }
    }

    fun updateAddress(address: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepository.updateAddress(currentUserId, address)
            _userProfile.value = _userProfile.value?.copy(address = address)
        }
    }

    fun logout() {
        auth.signOut()
        _userProfile.value = null
    }
}
