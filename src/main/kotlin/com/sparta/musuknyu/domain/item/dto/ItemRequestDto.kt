package com.sparta.musuknyu.domain.item.dto

data class ItemRequestDto (
    val itemName: String,
    val price: Long,
    val description: String,
    val stock: Long,
    val canPurchase: Boolean,
    val sales: Long,
)