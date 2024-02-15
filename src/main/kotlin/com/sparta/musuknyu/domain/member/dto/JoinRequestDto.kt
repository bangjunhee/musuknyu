package com.sparta.musuknyu.domain.member.dto

data class JoinRequestDto (
    val email: String,
    val nickname: String,
    val password: String,
    val address: String
)