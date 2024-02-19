package com.sparta.musuknyu.domain.item.service

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.dto.ItemRequestDto
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemEntity
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.item.repository.ItemRepository
import com.sparta.musuknyu.exception.ModelNotFoundException
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
    private val cacheManager: CaffeineCacheManager
): ItemService {
    override fun countKeywords(keyword: String) {
        val cache = cacheManager.getCache("SearchCounts")
        val currentCount = cache?.get(keyword, Integer::class.java)?.toInt()?.plus(1) ?: 1
        cache?.put(keyword, currentCount)
        log.info("keyword '$keyword' Count: $currentCount")
    }

    @Cacheable(key = "#search", value = ["keyword"], unless = "#search.trim().isEmpty()")
    override fun searchItem(search: String): List<ItemResponseDto> {
        log.info("keyword $search cached")
        return itemRepository.searchItemList(search).map { it.toResponseDto() }
    }

    override fun getItemList(): List<ItemResponseDto> {
        val itemList = itemRepository.findAll()
        return itemList.map{ it.toResponseDto() }
    }

    override fun getItemById(itemId: Long): ItemResponseDto {
        val item = itemRepository.findByIdOrNull(itemId)
            ?: throw ModelNotFoundException("ItemEntity", itemId)
        return item.toResponseDto()
    }

    @Transactional
    override fun addItem(request: ItemRequestDto): ItemResponseDto {
        val item = itemRepository.save(ItemEntity.toEntity(request))
        return item.toResponseDto()
    }

    @Transactional
    override fun updateItem(itemId: Long, request: ItemRequestDto): ItemResponseDto {
        val item = itemRepository.findByIdOrNull(itemId)
            ?: throw ModelNotFoundException("ItemEntity", itemId)
        item.updateItem(request)
        return item.toResponseDto()
    }

    @Transactional
    override fun deleteItem(itemId: Long) {
        val item = itemRepository.findByIdOrNull(itemId)
            ?: throw ModelNotFoundException("ItemEntity", itemId)
        item.isDeleted = true
    }

    //글 목록 조회 - 페이징 + 커스텀 정렬 + N일전 게시글 조회 (동적쿼리)
    override fun getItemListPaginated(
        page: Int,
        sortOrder: SortOrder?,
        itemTag: ItemTag,
        keywords: String?
    ): Page<ItemResponseDto> {
        return itemRepository.findByPageable(page, sortOrder, itemTag, keywords).map { it.toResponseDto() }
    }
}