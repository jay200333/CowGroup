package com.example.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import com.example.model.MyPageInfo
import com.example.model.MyPageUserInfo
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class MyPageUIState(
    val isLoading: Boolean = false,
    val message: String = "",
    val myPageInfo: MyPageInfo = MyPageInfo(
        userInfo = MyPageUserInfo(
            name = "",
            gender = "",
            birth = "",
            mbti = "",
        ),
        eventList = emptyList(),
        bookmarkList = emptyList()
    )
)

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _myPageUIState: MutableStateFlow<MyPageUIState> = MutableStateFlow(MyPageUIState())
    val myPageUIState: StateFlow<MyPageUIState> = _myPageUIState.asStateFlow()

    init {
        getMyPage()
    }

    private fun getMyPage() {
        viewModelScope.launch {
            _myPageUIState.update { state -> state.copy(isLoading = true) }
            try {
                val myPageInfo = userRepository.getMyPage()
                _myPageUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        myPageInfo = myPageInfo
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _myPageUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "마이 페이지 정보를 불러오는데 실패했습니다.",
                    )
                }
            } catch (e: Exception) {
                _myPageUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }
}