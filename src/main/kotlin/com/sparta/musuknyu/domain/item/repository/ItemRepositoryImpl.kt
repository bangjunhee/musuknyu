package com.sparta.musuknyu.domain.item.repository

import com.querydsl.core.BooleanBuilder
import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.entity.ItemEntity
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.item.entity.QItemEntity
import com.sparta.musuknyu.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.*
import org.springframework.stereotype.Repository

@Repository
class ItemRepositoryImpl: QueryDslSupport(), QueryItemRepository{
    private val item = QItemEntity.itemEntity

    override fun searchItemList(search: String): List<ItemEntity> {
        return queryFactory.selectFrom(item)
            .where(item.itemName.containsIgnoreCase(search))
            .fetch()
    }

    override fun findByPageable(page: Int, sortOrder: SortOrder?, itemTag: ItemTag, keywords: String?): Page<ItemEntity> {
        val whereClause = BooleanBuilder()
        val pageable: PageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, sortOrder.toString()))

        keywords?.let { whereClause.and(item.itemName.contains(it)) }
        if(itemTag != ItemTag.ALL) {
            itemTag.let { whereClause.and(item.itemTag.eq(itemTag))}
        }
        val totalCount = queryFactory.select(item.count()).from(item).where(whereClause).fetchOne() ?: 0L
        val query = queryFactory.selectFrom(item)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        if (pageable.sort.isSorted) {
            when (pageable.sort.first()?.property) {
                "CREATED_AT" -> query.orderBy(item.createdAt.asc())
                "ITEM_NAME" -> query.orderBy(item.itemName.asc())
                "PRICE" -> query.orderBy(item.price.asc())
                "SALES" -> query.orderBy(item.sales.desc())
                else -> query.orderBy(item.id.asc())
            }
        }

        val contents = query.fetch()

        return PageImpl(contents, pageable, totalCount)

    }
}