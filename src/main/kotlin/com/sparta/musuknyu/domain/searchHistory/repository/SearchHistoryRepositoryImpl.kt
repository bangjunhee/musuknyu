package com.sparta.musuknyu.domain.searchHistory.repository

import com.sparta.musuknyu.domain.searchHistory.entity.QSearchHistoryEntity
import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity
import com.sparta.musuknyu.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class SearchHistoryRepositoryImpl: QuerySearchRepository, QueryDslSupport(){
    private val searchHistory = QSearchHistoryEntity.searchHistoryEntity
    override fun getPopularKeywords(): List<SearchHistoryEntity> {
        return queryFactory.selectFrom(searchHistory)
            .orderBy(searchHistory.score.desc())
            .limit(10)
            .fetch()
    }
}