package com.sparta.musuknyu.domain.member.service

import com.sparta.musuknyu.domain.member.dto.JoinRequestDto
import com.sparta.musuknyu.domain.member.dto.LoginRequestDto
import com.sparta.musuknyu.domain.member.dto.LoginResponseDto
import com.sparta.musuknyu.domain.member.dto.MemberResponseDto
import com.sparta.musuknyu.domain.member.entity.MemberEntity
import com.sparta.musuknyu.domain.member.entity.toResponse
import com.sparta.musuknyu.domain.member.repository.MemberRepository
import com.sparta.musuknyu.exception.InvalidCredentialException
import com.sparta.musuknyu.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
): MemberService {

    override fun join(request: JoinRequestDto): MemberResponseDto {
        return memberRepository.save(
            MemberEntity(
                email = request.email,
                nickname = request.nickname,
                password = passwordEncoder.encode(request.password),
                address = request.address,
                role = "회원"
            )
        ).toResponse()
    }

    override fun login(request: LoginRequestDto): LoginResponseDto {

        //확인사항1: 이메일 존재 여부 확인
        val member = memberRepository.findByEmail(request.email)
            ?: throw InvalidCredentialException("이메일 또는 비밀번호를 확인해주세요.")

        //확인사항2: 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(request.password, member.password))
            throw InvalidCredentialException("이메일 또는 비밀번호를 확인해주세요.")

        return LoginResponseDto(
            accessToken = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                email = member.email,
                role = member.role
            )
        )
    }
}