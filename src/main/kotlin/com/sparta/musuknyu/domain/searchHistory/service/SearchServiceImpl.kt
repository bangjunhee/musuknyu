package com.sparta.musuknyu.domain.searchHistory.service

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.item.repository.ItemRepository
import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import org.hibernate.query.sqm.tree.SqmNode
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchServiceImpl (
    private val itemRepository: ItemRepository,
    private val redisTemplate: RedisTemplate<String, Any>,
): SearchService {

    override fun getPopularKeywords(): List<KeywordResponseDto> {
        val keywordEntries = redisTemplate.opsForZSet().reverseRangeWithScores("rank", 0, 9)
            ?: emptySet()
        return keywordEntries.map { entry ->
            val keyword = entry.value as String
            val score = entry.score!!.toInt()
            KeywordResponseDto(keyword = keyword, score = score)
        }
    }

    @Transactional
    @Cacheable(key = "#keywords", cacheNames = ["keyword"], condition = "#keywords != null")
    override fun getItemListPaginated(
        page: Int,
        sortOrder: SortOrder?,
        itemTag: ItemTag,
        keywords: String?
    ): Page<ItemResponseDto> {
        val items = itemRepository.findByPageable(page, sortOrder, itemTag, keywords)
        return items.map { it.toResponseDto() }
    }

    override fun saveKeywordsByZSet(keywords: String?) {
        if (keywords == null){
            SqmNode.log.info("검색어가 없습니다")
            return
        }
        if (!redisTemplate.hasKey("rank")) {
            redisTemplate.opsForZSet().add("rank", keywords, 1.0)
        } else {
            redisTemplate.opsForZSet().incrementScore("rank", keywords, 1.0)
        }
    }
}