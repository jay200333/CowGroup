package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.data.repository.UserRepository
import com.example.model.EventMember
import com.example.navigation.MemberRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class MemberUIState(
    val isLoading: Boolean = false,
    val eventMember: EventMember = EventMember(memberInfoList = emptyList(), memberCount = 0),
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

    fun getMemberList() {
        viewModelScope.launch {
            _memberUIState.value = MemberUIState(isLoading = true)
            try {
                val memberList = userRepository.getEventMemberList(eventId)
                _memberUIState.update {
                    it.copy(isLoading = false, eventMember = memberList)
                }
            } catch (e: HttpException) {
                _memberUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "멤버 리스트를 가져오는데 실패했습니다."
                    )
                }
            } catch (e: Exception) {
                _memberUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun setMessageClear() {
        _memberUIState.update { state ->
            state.copy(message = "")
        }
    }
}