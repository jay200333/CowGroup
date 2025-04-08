package com.example.login.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.model.SignUpStep1Info
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject

data class SignUpUIState(
    val isLoading: Boolean = false,
)

@HiltViewModel
class SignUpExtraInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _signUpUIState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val signUpUIState: StateFlow<SignUpUIState> = _signUpUIState.asStateFlow()
    private val signUpStep1Info: SignUpStep1Info = requireNotNull(
        savedStateHandle.get<String>("signUpStep1Info")
            ?.let { string -> Json.decodeFromString<SignUpStep1Info>(string) }) { "event is required." }

    init {
        Log.d("SignUpExtraInfoViewModel", "signUpStep1Info: ${signUpStep1Info.email}")
    }
}