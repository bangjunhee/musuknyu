package com.sparta.musuknyu.common.schedule

import com.sparta.musuknyu.domain.searchHistory.service.SearchServiceImpl
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val searchService: SearchServiceImpl
) {
    @Scheduled(fixedDelay = 30000)
    fun deleteCacheAndCacheToDatabase() {
        val cache = redisTemplate.opsForHash<String, Long>().entries("SearchCounts")
        cache.forEach { (keyword, count) ->
            searchService.updateOrCreateSearchCount(keyword, count)
            log.info("Cache data saved to database $keyword $count")
        }
        redisTemplate.delete("SearchCounts")
        log.info("Cache data saved to database")
    }
}
