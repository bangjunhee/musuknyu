package com.sparta.musuknyu.infra.redis.Service

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    @CacheEvict(cacheNames = ["SearchCounts"], allEntries = true)
    fun deleteSearchCountsKeys(){
        log.info("delete SearchCounts caches.")
    }
}