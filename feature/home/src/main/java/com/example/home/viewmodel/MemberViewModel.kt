package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.data.repository.UserRepository
import com.example.model.MemberInfo
import com.example.navigation.MemberRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MemberUIState(
    val isLoading: Boolean = false,
    val memberList: List<MemberInfo> = emptyList(),
    val message: String = "",
)

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val eventId: Int = savedStateHandle.toRoute<MemberRoute>().eventId
    private val _memberUIState: MutableStateFlow<MemberUIState> = MutableStateFlow(MemberUIState())
    val memberUIState: StateFlow<MemberUIState> = _memberUIState.asStateFlow()

    init {
        getMemberList()
    }

    private fun getMemberList() {
        Log.d("MemberViewModel", "$eventId")
    }
}