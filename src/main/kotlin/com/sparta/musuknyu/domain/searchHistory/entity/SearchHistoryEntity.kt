package com.sparta.musuknyu.domain.searchHistory.entity

import jakarta.persistence.*

@Entity
@Table(name = "search_history")
class SearchHistoryEntity(
    @Column(name = "keyword")
    var keywords: String? = null,

    @Column(name = "search_count")
    var totalSearchCount: Long = 0,

    @Column(name = "daily_count")
    var previousSearchCount: Long = 0,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var searchId: Long? = null

    fun updateSearchCount(keyword: String, count: Long){
        keywords = keyword
        totalSearchCount = count + previousSearchCount
        previousSearchCount = count
    }

    fun createSearchCount(keyword: String, count: Long){
        keywords = keyword
        totalSearchCount = count
        previousSearchCount = count
    }

}