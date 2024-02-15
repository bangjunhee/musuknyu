package com.sparta.musuknyu.domain.item.repository

import com.sparta.musuknyu.domain.item.entity.ItemEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QueryItemRepository {
    fun searchItemList(search: String): List<ItemEntity>
    fun findByPageable(pageable: Pageable, itemName: String?,price: Long?, description: String?, stock: Long?, canPurchase: Boolean?, sales: Long?, daysAgo: Long?): Page<ItemEntity>
}