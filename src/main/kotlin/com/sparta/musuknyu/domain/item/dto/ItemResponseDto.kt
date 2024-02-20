package com.sparta.musuknyu.domain.item.dto

import com.sparta.musuknyu.domain.item.entity.ItemTag
import java.time.ZonedDateTime

data class ItemResponseDto (
    val id: Long,
    val itemName: String,
    val price: Long,
    val description: String,
    val stock: Long,
    val canPurchase: Boolean,
    val sales: Long,
    val itemTag: ItemTag,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)