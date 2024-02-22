package com.sparta.musuknyu.domain.searchHistory.entity

import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import jakarta.persistence.*

@Entity
@Table(name = "search_history")
class SearchHistoryEntity(
    @Column(name = "keyword")
    var keywords: String,

    @Column(name = "score")
    var score: Double? = null,
    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var searchId: Long = 0

    fun toResponseDto(): KeywordResponseDto {
        return KeywordResponseDto(
            keyword = this.keywords,
        )
    }
}