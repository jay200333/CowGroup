package com.example.data.repository

import com.example.model.LoginInfo
import com.example.model.MemberInfo
import com.example.model.MyPageInfo
import com.example.model.Profile
import com.example.model.SignUpInfo

interface UserRepository {
    suspend fun login(loginInfo: LoginInfo): String?

    suspend fun signUp(signUpInfo: SignUpInfo)

    suspend fun checkUsername(username: String): Boolean

    suspend fun sendAuthCode(email: String): Boolean

    suspend fun getProfile(): Profile

    suspend fun editProfile(profile: Profile)

    suspend fun getMyPage(): MyPageInfo

    suspend fun getMemberList(eventId: Int): List<MemberInfo>
}
