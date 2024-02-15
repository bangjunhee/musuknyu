package com.sparta.musuknyu.domain.item.service

import com.sparta.musuknyu.domain.item.dto.ItemRequestDto
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ItemService {
    fun searchItem(search: String): List<ItemResponseDto>
    fun getItemList(): List<ItemResponseDto>
    fun getItemById(itemId: Long): ItemResponseDto
    fun addItem(itemRequestDto: ItemRequestDto): ItemResponseDto
    fun updateItem(itemId: Long, itemRequestDto: ItemRequestDto): ItemResponseDto
    fun deleteItem(itemId: Long)
    fun getItemListPaginated(pageable: Pageable, itemName: String?,price: Long?, description: String?, stock: Long?, canPurchase: Boolean?, sales: Long?, daysAgo: Long?): Page<ItemResponseDto>
}