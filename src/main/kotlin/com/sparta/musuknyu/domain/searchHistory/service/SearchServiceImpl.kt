package com.sparta.musuknyu.domain.searchHistory.service

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.item.repository.ItemRepository
import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import com.sparta.musuknyu.domain.searchHistory.repository.SearchHistoryRepositoryImpl
import org.hibernate.query.sqm.tree.SqmNode
import org.springframework.data.domain.Page
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl (
    private val itemRepository: ItemRepository,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl
): SearchService {

    override fun getPopularKeywords(): List<KeywordResponseDto>{
        val keywordList = searchHistoryRepositoryImpl.getPopularKeywords()
        return keywordList.map { it.toResponseDto() }
    }

    override fun getItemListPaginated(
        page: Int,
        sortOrder: SortOrder?,
        itemTag: ItemTag,
        keywords: String?
    ): Page<ItemResponseDto> {
        val items = itemRepository.findByPageable(page, sortOrder, itemTag, keywords)
        if (keywords == null) {
            SqmNode.log.info("검색어가 없습니다. ")
        } else {
            if (!redisTemplate.hasKey("키워드 랭킹")) {
                redisTemplate.opsForZSet().add("키워드 랭킹", keywords, 1.0)
            } else {
                redisTemplate.opsForZSet().incrementScore("키워드 랭킹", keywords, 1.0)
            }
        }
        return items.map { it.toResponseDto() }
    }
}