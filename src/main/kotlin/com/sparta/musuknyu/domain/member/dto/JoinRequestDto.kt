package com.sparta.musuknyu.domain.member.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class JoinRequestDto (
    @field: Email(message = "E-mail 을 다시 입력해주세요.")
    val email: String,
    @field: Size(min = 2, max = 10, message = "nickname 을 알파벳과 숫자로 구성해 2자 이상 10자 이하로 설정해주세요.")
    val nickname: String,
    @field: Size(min = 6, max = 12, message = "password 를 알파벳과 숫자로 구성해 6자 이상 12자 이하로 설정해주세요.")
    val password: String,
    @field: Size(min = 6, max = 30, message = "주소를 다시 입력해주세요.")
    val address: String
)