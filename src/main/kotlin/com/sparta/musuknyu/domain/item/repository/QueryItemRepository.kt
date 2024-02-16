package com.sparta.musuknyu.domain.item.repository

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.entity.ItemEntity
import com.sparta.musuknyu.domain.item.entity.ItemTag
import org.springframework.data.domain.Page

interface QueryItemRepository {
    fun searchItemList(search: String): List<ItemEntity>
    fun findByPageable(page: Int,sortOrder: SortOrder?, itemTag: ItemTag, keywords: String?): Page<ItemEntity>
}