package com.sparta.musuknyu.domain.item.repository

import com.sparta.musuknyu.domain.item.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<ItemEntity, Long>, QueryItemRepository