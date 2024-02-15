package com.sparta.musuknyu.domain.item.repository

import com.querydsl.core.BooleanBuilder
import com.sparta.musuknyu.domain.item.entity.ItemEntity
import com.sparta.musuknyu.domain.item.entity.QItemEntity
import com.sparta.musuknyu.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ItemRepositoryImpl: QueryDslSupport(), QueryItemRepository{
    private val item = QItemEntity.itemEntity

    override fun searchItemList(search: String): List<ItemEntity> {
        return queryFactory.selectFrom(item)
            .where(item.itemName.containsIgnoreCase(search))
            .fetch()
    }

    override fun findByPageable(pageable: Pageable, itemName: String?,price: Long?, description: String?, stock: Long?, canPurchase: Boolean?, sales: Long?, daysAgo: Long?): Page<ItemEntity> {
        val whereClause = BooleanBuilder()

        itemName?.let { whereClause.and(item.itemName.contains(itemName)) }
        price?.let { whereClause.and(item.price.eq(price)) }
        description?.let { whereClause.and(item.description.contains(description)) }
        stock?.let { whereClause.and(item.stock.eq(stock)) }
        canPurchase?.let { whereClause.and(item.canPurchase.eq(canPurchase)) }
        sales?.let { whereClause.and(item.sales.eq(sales)) }
        daysAgo?.let {
            val startDate = LocalDateTime.now().minusDays(daysAgo)
            val endDate = LocalDateTime.now().minusDays(daysAgo-1)
            whereClause.and(item.createdAt.between(startDate, endDate)) }

        val totalCount = queryFactory.select(item.count()).from(item).where(whereClause).fetchOne() ?: 0L
        val query = queryFactory.selectFrom(item)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        if (pageable.sort.isSorted) {
            when (pageable.sort.first()?.property) {
                "id" -> query.orderBy(item.id.asc())
                "itemName" -> query.orderBy(item.itemName.asc())
                "price" -> query.orderBy(item.price.asc())
                "description" -> query.orderBy(item.description.asc())
                "stock" -> query.orderBy(item.stock.asc())
                "canPurchase" -> query.orderBy(item.canPurchase.asc())
                "sales" -> query.orderBy(item.sales.asc())
                else -> query.orderBy(item.id.asc())
            }
        }

        val contents = query.fetch()

        return PageImpl(contents, pageable, totalCount)

    }
}