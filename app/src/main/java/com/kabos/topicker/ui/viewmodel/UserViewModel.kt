package com.kabos.topicker.ui.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Stable
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){

    fun createUser() = viewModelScope.launch {
        runCatching {
            userRepository.createUser()
        }
    }

    // todo 仮実装
    fun getUser() = viewModelScope.launch {
        runCatching {
            val result = userRepository.getUser()
            Timber.d("--user success $result")
        }.onFailure {
            createUser()
        }
    }
}
