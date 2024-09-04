package com.example.movaapp.repository

import com.example.movaapp.utils.NetworkResponseState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun login(email: String, password: String): Flow<NetworkResponseState<AuthResult>> = flow {
        emit(NetworkResponseState.Loading)
        val userLogin = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(NetworkResponseState.Success(userLogin))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }.flowOn(Dispatchers.IO)

    fun register(email: String, password: String): Flow<NetworkResponseState<AuthResult>> = flow {
        emit(NetworkResponseState.Loading)
        val userRegister = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(NetworkResponseState.Success(userRegister))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }.flowOn(Dispatchers.IO)
}