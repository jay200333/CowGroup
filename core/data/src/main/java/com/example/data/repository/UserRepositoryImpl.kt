package com.example.data.repository

import android.util.Log
import com.example.model.LoginInfo
import com.example.model.MemberInfo
import com.example.model.MyPageInfo
import com.example.model.Profile
import com.example.model.SignUpInfo
import com.example.network.model.toMemberInfo
import com.example.network.model.toMyPageInfo
import com.example.network.model.toProfile
import com.example.network.retrofit.CowGroupApi
import retrofit2.HttpException
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val api: CowGroupApi,
) : UserRepository {
    override suspend fun login(loginInfo: LoginInfo): String? =
        try {
            val response = api.login(loginInfo = loginInfo)
            val token = response.headers()["Authorization"]
            if (token.isNullOrEmpty()) {
                throw Exception("토큰이 없습니다.")
            }
            token
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }

    override suspend fun signUp(signUpInfo: SignUpInfo) {
        try {
            api.signUp(signUpInfo = signUpInfo)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun checkUsername(username: String): Boolean {
        try {
            val response = api.checkUsername(username = username)
            return response.data.verificationPassed
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun sendAuthCode(email: String): Boolean {
        try {
            val response = api.sendAuthCode(email = email)
            return response.data.verificationPassed
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProfile(): Profile {
        try {
            val response = api.getProfile()
            return response.data.toProfile()
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun editProfile(profile: Profile) {
        try {
            api.editProfile(profile = profile)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMyPage(): MyPageInfo {
        try {
            val response = api.getMyPage()
            return response.data.toMyPageInfo()
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMemberList(eventId: Int): List<MemberInfo> {
        try {
            val response = api.getMemberList(eventId)
            val memberList = response.data.participants.map { it.toMemberInfo() }
            Log.d("UserRepository", "$memberList")
            return memberList
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
