package com.sparta.musuknyu.domain.item.repository

import com.sparta.musuknyu.domain.item.entity.ItemEntity

interface QueryItemRepository {
    fun searchItemList(search: String): List<ItemEntity>
}