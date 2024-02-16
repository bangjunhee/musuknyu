package com.sparta.musuknyu.domain.searchHistory.entity

import jakarta.persistence.*

@Entity
@Table(name = "search_history")
class SearchHistoryEntity(
    @Column(name = "keyword")
    var keyword: String? = null,

    @Column(name = "search_count")
    var searchCount: Int = 0,

    @Column(name = "daily_count")
    var dailyCount: Int = 0,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var searchId: Long? = null

}