package com.sparta.musuknyu.domain.dummy.service

import com.sparta.musuknyu.domain.item.entity.ItemEntity
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.item.repository.ItemRepository
import net.datafaker.Faker
import org.springframework.stereotype.Service

@Service
class DummyServiceImpl(
    private val itemRepository: ItemRepository
): DummyService {

    override fun addDummyItems(count: Int) {
        val faker = Faker()
        repeat(count) {
            val clothingTypes  = mapOf(
                ItemTag.TOP to listOf("shirts", "jacket", "sweater", "blouse", "hoodie", "vest", "t-shirt"),
                ItemTag.BOTTOM to listOf("jeans", "shorts", "skirt", "pants", "sweatpants"),
                ItemTag.OUTER to listOf("jacket", "sweater", "coat", "vest", "hoodie"),
                ItemTag.SHOES to listOf("shoes", "sneakers", "boots", "sandals"),
                ItemTag.BAGS to listOf("bag", "backpack", "tote", "clutch"),
                ItemTag.BEAUTY to listOf("bracelet", "necklace", "earrings", "ring", "hat", "scarf"),
                ItemTag.GLASSES to listOf("glasses", "sunglasses", "vintage glasses", "sport glasses")
            )
            val colors = listOf(
                "black", "white", "red", "blue", "green", "yellow",
                "orange", "pink", "purple", "brown", "gray", "beige"
            )
            val tag = ItemTag.entries.filterNot { it == ItemTag.ALL }.random()
            val itemType = clothingTypes [tag]?.random()
            val itemName = "${colors.random()}_$itemType"+"_${(100..999).random()}"
            val stock = (0..1000).random().toLong()
            val item = ItemEntity(
                itemName = itemName,
                price = (10..100).random() * 1000L,
                description = "해당 상품을 설명합니다",
                stock = stock,
                canPurchase = stock > 0,
                sales = faker.number().randomNumber(2, true),
                itemTag = tag,
                isDeleted = false
            )
            //상품 저장
            itemRepository.save(item)
        }
    }
}