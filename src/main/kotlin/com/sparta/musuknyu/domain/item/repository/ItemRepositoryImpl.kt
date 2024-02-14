package com.sparta.musuknyu.domain.item.repository

import com.sparta.musuknyu.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class ItemRepositoryImpl: QueryDslSupport(), QueryItemRepository