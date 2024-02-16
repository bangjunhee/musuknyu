package com.sparta.musuknyu.domain.item.entity

import com.sparta.musuknyu.domain.item.dto.ItemRequestDto
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "items")
@SQLRestriction("is_deleted is false")
data class ItemEntity(
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false)
    var itemTag: ItemTag,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    companion object {
        fun toEntity(request: ItemRequestDto): ItemEntity {
            return ItemEntity(
                itemName = request.itemName,
                price = request.price,
                description = request.description,
                stock = request.stock,
                canPurchase = request.canPurchase,
                sales = request.sales,
                itemTag = request.itemTag,
                isDeleted = false
            )
        }
    }

    fun updateItem(request: ItemRequestDto) {
        itemName = request.itemName
        price = request.price
        description = request.description
        stock = request.stock
        canPurchase = request.canPurchase
        sales = request.sales
        itemTag = request.itemTag
        updatedAt = LocalDateTime.now()
    }

    fun toResponseDto(): ItemResponseDto {
        return ItemResponseDto(
            id = id!!,
            itemName = itemName,
            price = price,
            description = description,
            stock = stock,
            canPurchase = canPurchase,
            sales = sales,
            createdAt = createdAt,
            updatedAt = updatedAt,
            itemTag = itemTag
        )
    }

}



