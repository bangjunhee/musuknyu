package com.sparta.musuknyu.domain.searchHistory.service

import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity
import com.sparta.musuknyu.domain.searchHistory.repository.SearchHistoryRepository
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchService (private val searchHistoryRepository: SearchHistoryRepository){

    @Transactional
    fun updateOrCreateSearchCount(keyword: String, count: Long){
        val searchCount = searchHistoryRepository.findByKeywords(keyword)
            ?: SearchHistoryEntity(
                keywords = keyword,
                totalSearchCount = count,
                previousSearchCount = count
            )
        searchCount.updateSearchCount(keyword, count)
        searchHistoryRepository.save(searchCount)
    }
}