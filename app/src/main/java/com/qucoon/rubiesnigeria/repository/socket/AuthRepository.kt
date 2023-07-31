package com.qucoon.rubiesnigeria.repository.socket

import com.qucoon.rubiesnigeria.base.BaseRepository
import com.qucoon.rubiesnigeria.model.request.login.LoginRequest
import com.qucoon.rubiesnigeria.model.request.signup.SignupRequest
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.model.response.signup.SignupResponse
import com.qucoon.rubiesnigeria.network.RubiesApi
import com.qucoon.rubiesnigeria.networkUtility.UseCaseResult
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.savePref

interface AuthRepository {
    suspend fun loginUser(request: LoginRequest): UseCaseResult<LoginResponse>
    suspend fun signUpUser(request: SignupRequest): UseCaseResult<SignupResponse>
}
    class AuthRepositoryImpl(
        private val rubiesApi: RubiesApi, private val paperPrefs: PaperPrefs) : BaseRepository(), AuthRepository {

        override suspend fun loginUser(request: LoginRequest): UseCaseResult<LoginResponse> {
            return safeApiCall(request, rubiesApi::loginUser, { it.responseCode == null }, this::saveLoginResponse
            )
        }

        override suspend fun signUpUser(request: SignupRequest): UseCaseResult<SignupResponse> {
            return safeApiCall(request, rubiesApi::sigupUser) { it.responseCode == null }
        }


        private suspend fun saveLoginResponse(request: LoginRequest, response: LoginResponse) {
            paperPrefs.savePref(PaperPrefs.AUTH_TOKEN, response.token ?: "")
            paperPrefs.savePref(PaperPrefs.USER_PHONE, request.phone)

        }
    }
