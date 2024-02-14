package com.sparta.musuknyu.domain.member.repository

import com.sparta.musuknyu.domain.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<MemberEntity, Long>{
    fun findByEmail(email: String) : MemberEntity
}