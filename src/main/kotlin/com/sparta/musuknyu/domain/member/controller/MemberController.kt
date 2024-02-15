package com.sparta.musuknyu.domain.member.controller

import com.sparta.musuknyu.domain.member.dto.JoinRequestDto
import com.sparta.musuknyu.domain.member.dto.LoginRequestDto
import com.sparta.musuknyu.domain.member.dto.LoginResponseDto
import com.sparta.musuknyu.domain.member.dto.MemberResponseDto
import com.sparta.musuknyu.domain.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "member", description = "회원")
@RequestMapping("/members")
@RestController
class MemberController(
    private val memberService: MemberService,
) {
    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    fun join(
        @Valid @RequestBody signUpRequest: JoinRequestDto
    ): ResponseEntity<MemberResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.join(signUpRequest))
    }

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequestDto: LoginRequestDto,
    ): ResponseEntity<LoginResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.login(loginRequestDto))
    }


}