package com.sparta.musuknyu.domain.searchHistory.repository

import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SearchHistoryRepository: JpaRepository<SearchHistoryEntity, Long> {
}