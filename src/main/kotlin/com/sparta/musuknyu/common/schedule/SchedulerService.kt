package com.sparta.musuknyu.common.schedule

import com.github.benmanes.caffeine.cache.Cache
import com.sparta.musuknyu.domain.searchHistory.service.SearchServiceImpl
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


@Service
class SchedulerService(
    private val caffeineCacheManager: CacheManager,
    private val searchService: SearchServiceImpl
) {
    @Scheduled(fixedDelay = 30000)
    fun deleteCacheAndCacheToDatabase() {
        val cache = caffeineCacheManager.getCache("SearchCounts")?.nativeCache as? Cache<*, *>
        cache?.asMap()?.forEach{
            (keyword, count) -> searchService.updateOrCreateSearchCount(keyword.toString(), count.toString().toLong())
        }
        cache?.invalidateAll()
        log.info("Save cache data To Database")
    }
}