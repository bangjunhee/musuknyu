package com.sparta.musuknyu.domain.member.entity

import com.sparta.musuknyu.domain.member.dto.MemberResponseDto
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@Entity
@Table(name = "member")
class MemberEntity (

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "address", nullable = false)
    val address: String,

    @Column(name = "role", nullable = false)
    val role: String

){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun MemberEntity.toResponse(): MemberResponseDto {
    return MemberResponseDto(
        id = id!!,
        email = email,
        nickname = nickname,
        address = address
    )
}