package com.sparta.musuknyu.domain.member.service

import com.sparta.musuknyu.domain.member.dto.JoinRequestDto
import com.sparta.musuknyu.domain.member.dto.LoginRequestDto
import com.sparta.musuknyu.domain.member.dto.LoginResponseDto
import com.sparta.musuknyu.domain.member.dto.MemberResponseDto

interface MemberService {
    fun join(request: JoinRequestDto): MemberResponseDto
    fun login(request: LoginRequestDto): LoginResponseDto
}