package com.sparta.musuknyu.domain.item.repository

import com.sparta.musuknyu.domain.item.entity.ItemEntity
import com.sparta.musuknyu.domain.item.entity.QItemEntity
import com.sparta.musuknyu.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class ItemRepositoryImpl: QueryDslSupport(), QueryItemRepository{
    private val item = QItemEntity.itemEntity

    override fun searchItemList(search: String): List<ItemEntity> {
        return queryFactory.selectFrom(item)
            .where(item.itemName.containsIgnoreCase(search))
            .fetch()
    }
}