package com.sparta.musuknyu.domain.searchHistoryRedis.service

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.item.repository.ItemRepository
import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity
import com.sparta.musuknyu.domain.searchHistory.repository.SearchHistoryRepository
import com.sparta.musuknyu.domain.searchHistory.service.SearchService
import org.hibernate.query.sqm.tree.SqmNode
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RedisSearchServiceImpl (
    private val searchHistoryRepository: SearchHistoryRepository,
    private val itemRepository: ItemRepository,
    private val redisTemplate: RedisTemplate<String, String>,
    private val redisCacheManager: RedisCacheManager
): SearchService {

    private val zSetOperations = redisTemplate.opsForZSet()

    override fun getPopularKeywords(): List<KeywordResponseDto>{
        val keywordList = searchHistoryRepository.getPopularKeywords()
        return keywordList.map { it.toResponseDto() }
    }

    override fun countKeywords(keyword: String?) {
        if (keyword == null) {
            SqmNode.log.info("Keyword is null, skipping count update.")
            return
        }
        val cache = redisCacheManager.getCache("SearchCounts")
        val currentCount = cache?.get(keyword, Integer::class.java)?.toInt()?.plus(1) ?: 1
        cache?.put(keyword, currentCount)
        SqmNode.log.info("keyword '$keyword' Count: $currentCount")
    }


    override fun searchItem(search: String): List<ItemResponseDto> {
        SqmNode.log.info("keyword $search cached")
        return itemRepository.searchItemList(search).map { it.toResponseDto() }
    }

    @Cacheable(key = "#keywords", value = ["keyword"], condition = "#keywords != null")
    override fun getItemListPaginated(
        page: Int,
        sortOrder: SortOrder?,
        itemTag: ItemTag,
        keywords: String?
    ): Page<ItemResponseDto> {
        return itemRepository.findByPageable(page, sortOrder, itemTag, keywords).map { it.toResponseDto() }
    }

    @Transactional
    override fun updateOrCreateSearchCount(keyword: String, count: Long){
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