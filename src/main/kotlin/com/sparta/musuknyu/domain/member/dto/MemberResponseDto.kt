package com.sparta.musuknyu.domain.member.dto

data class MemberResponseDto (
    val id: Long,
    val email: String,
    val nickname: String,
    val address: String
)