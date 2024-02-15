package com.sparta.musuknyu.domain.item.dto

import com.sparta.musuknyu.domain.item.entity.ItemTag
import jakarta.validation.constraints.Size

data class ItemRequestDto (
    @field: Size(min = 1, max = 15, message = "itemName 을 1자 이상 15자 이하로 설정해주세요.")
    val itemName: String,
    val price: Long,
    @field: Size(min = 2, max = 500, message = "description 을 2자 이상 500자 이하로 설정해주세요.")
    val description: String,
    val stock: Long,
    val canPurchase: Boolean,
    val sales: Long,
    val itemTag: ItemTag
)