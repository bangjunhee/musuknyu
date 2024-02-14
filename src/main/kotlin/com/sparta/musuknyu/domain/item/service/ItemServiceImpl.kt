package com.sparta.musuknyu.domain.item.service

import com.sparta.musuknyu.domain.item.dto.ItemRequestDto
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemEntity
import com.sparta.musuknyu.domain.item.entity.toResponse
import com.sparta.musuknyu.domain.item.repository.ItemRepository
import com.sparta.musuknyu.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
): ItemService {

    override fun getItemList(): List<ItemResponseDto> {
        val itemList = itemRepository.findAll().filter { !it.isDeleted }
        return itemList.map{ it.toResponse() }
    }

    override fun getItemById(itemId: Long): ItemResponseDto {
        val item = itemRepository.findByIdOrNull(itemId)
            ?.takeIf { !it.isDeleted }
            ?: throw ModelNotFoundException("ItemEntity", itemId)
        return item.toResponse()
    }

    override fun addItem(request: ItemRequestDto): ItemResponseDto {
        return itemRepository.save(
            ItemEntity(
                itemName = request.itemName,
                price = request.price,
                description = request.description,
                stock = request.stock,
                canPurchase = request.canPurchase,
                sales = request.sales,
                isDeleted = false
            )
        ).toResponse()
    }

    override fun updateItem(itemId: Long, request: ItemRequestDto): ItemResponseDto {
        val item = itemRepository.findByIdOrNull(itemId)
            ?.takeIf { !it.isDeleted }
            ?: throw ModelNotFoundException("ItemEntity", itemId)
        item.itemName = request.itemName
        item.price = request.price
        item.description = request.description
        item.stock = request.stock
        item.canPurchase = request.canPurchase
        item.sales = request.sales
        return item.toResponse()

    }

    override fun deleteItem(itemId: Long) {
        val item = itemRepository.findByIdOrNull(itemId)
            ?.takeIf { !it.isDeleted }
            ?: throw ModelNotFoundException("ItemEntity", itemId)
        item.isDeleted = true
    }
}