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
    private val itemRepository: ItemRepository
): ItemService {
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
}