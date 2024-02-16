package com.sparta.musuknyu.common.schedule

import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


@Service
class SchedulerService {
    val logger = KotlinLogging.logger{}
    @Scheduled(fixedDelay = 180000)
    @CacheEvict(value = ["keyword"], allEntries = true)
    fun deleteCacheEveryThreeMinutes() {
        logger.trace{"Cache is deleted"}
    }
}