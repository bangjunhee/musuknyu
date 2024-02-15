package com.sparta.musuknyu.exception

data class InvalidCredentialException(
    override val message: String? = "The credential is invalid"
): RuntimeException(message)