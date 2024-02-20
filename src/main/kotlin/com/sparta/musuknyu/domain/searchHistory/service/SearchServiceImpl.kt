package com.sparta.musuknyu.domain.searchHistory.service

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.item.repository.ItemRepository
import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity
import com.sparta.musuknyu.domain.searchHistory.repository.SearchHistoryRepository
import org.hibernate.query.sqm.tree.SqmNode
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchServiceImpl (
    private val searchHistoryRepository: SearchHistoryRepository,
    private val itemRepository: ItemRepository,
    private val cacheManager: CaffeineCacheManager
): SearchService{

    override fun getPopularKeywords(): List<KeywordResponseDto>{
        val keywordList = searchHistoryRepository.getPopularKeywords()
        return keywordList.map { it.toResponseDto() }
    }
    override fun countKeywords(keyword: String?) {
        if (keyword == null) {
            SqmNode.log.info("Keyword is null, skipping count update.")
            return
        }
        val cache = cacheManager.getCache("SearchCounts")
        val currentCount = cache?.get(keyword, Integer::class.java)?.toInt()?.plus(1) ?: 1
        cache?.put(keyword, currentCount)
        SqmNode.log.info("keyword '$keyword' Count: $currentCount")
    }


    override fun searchItem(search: String): List<ItemResponseDto> {
        SqmNode.log.info("keyword $search cached")
        return itemRepository.searchItemList(search).map { it.toResponseDto() }
    }

//    @Cacheable(key = "#keywords", value = ["keyword"], #keywords.trim().isEmpty()")
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