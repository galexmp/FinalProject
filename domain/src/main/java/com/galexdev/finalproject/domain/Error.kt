package com.galexdev.finalproject.domain

sealed interface Error{
    class Server(val code: Int): Error
    object Connectivity: Error
    class Unknown(val message: String): Error
}

