package com.example.skillcinema.model.repository

sealed class State {
    object Loading : State()
    object Success : State()

    //    data class ServerError(val message: String) : State()
    object Error : State()
}