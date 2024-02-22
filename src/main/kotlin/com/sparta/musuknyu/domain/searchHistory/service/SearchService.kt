package com.sparta.musuknyu.domain.searchHistory.service

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import org.springframework.data.domain.Page

interface SearchService {
    fun getPopularKeywords(): List<KeywordResponseDto>
    fun getItemListPaginated(
        page: Int,
        sortOrder: SortOrder?,
        itemTag: ItemTag,
        keywords: String?
    ): Page<ItemResponseDto>
}