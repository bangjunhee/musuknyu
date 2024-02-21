package com.sparta.musuknyu.common.schedule

import com.sparta.musuknyu.domain.searchHistory.service.SearchServiceImpl
import com.sparta.musuknyu.infra.redis.Service.RedisService
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val searchService: SearchServiceImpl,
    private val redisService: RedisService
) {
    @Scheduled(fixedDelay = 30000)
    fun deleteCacheAndCacheToDatabase() {
        val cache = redisTemplate.opsForHash<String,Long>().entries("SearchCounts")
        cache.forEach { (keyword, count) ->
            log.info("key: $keyword, count: $count")
            searchService.updateOrCreateSearchCount(keyword, count)
        }
        redisService.deleteSearchCountsKeys()
        log.info("Cache data saved to database")
    }
}
