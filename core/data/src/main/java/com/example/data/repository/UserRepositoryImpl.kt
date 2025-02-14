package com.example.data.repository

import android.util.Log
import com.example.model.LoginInfo
import com.example.model.Profile
import com.example.model.SignUpInfo
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
            Log.d("loginResult", "$token")
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
            return response.data.exists
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun checkEmail(email: String): Boolean {
        try {
            val response = api.checkEmail(email = email)
            return response.data.exists
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUserInfo(): Profile {
        try {
            val response = api.getUserInfo()
            return response.data.toProfile()
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
