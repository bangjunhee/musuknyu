package com.sparta.musuknyu.domain.searchHistory.repository

import com.querydsl.core.BooleanBuilder
import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import com.sparta.musuknyu.domain.searchHistory.entity.QSearchHistoryEntity
import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity
import com.sparta.musuknyu.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class SearchHistoryRepositoryImpl: QuerySearchRepository, QueryDslSupport(){
    private val searchHistory = QSearchHistoryEntity.searchHistoryEntity
    override fun getPopularKeywords(): List<SearchHistoryEntity> {
        return queryFactory.selectFrom(searchHistory)
            .orderBy(searchHistory.previousSearchCount.desc())
            .limit(10)
            .fetch()
    }
}