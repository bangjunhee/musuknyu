package com.sparta.musuknyu.domain.item.entity

import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "item")
@SQLRestriction("is_deleted is false")
data class ItemEntity (

    @Column(name = "item_name", nullable = false)
    var itemName: String,

    @Column(name = "price", nullable = false)
    var price: Long,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "stock", nullable = false)
    var stock: Long,

    @Column(name = "can_purchase", nullable = false)
    var canPurchase: Boolean,

    @Column(name = "sales", nullable = false)
    var sales: Long,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean
){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun ItemEntity.toResponse(): ItemResponseDto {
    return ItemResponseDto(
        id = id!!,
        itemName = itemName,
        price = price,
        description = description,
        stock = stock,
        canPurchase = canPurchase,
        sales = sales
    )
}