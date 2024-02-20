package com.sparta.musuknyu.domain.searchHistory.repository
import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity

interface QuerySearchRepository {
    fun getPopularKeywords(): List<SearchHistoryEntity>
}