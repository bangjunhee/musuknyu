package com.sparta.musuknyu.common.schedule

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


//@Service
//class SchedulerService {
//    @Scheduled(fixedDelay = 5000)
//    @CacheEvict(value = ["keyword"], allEntries = true)
//    fun deleteCacheEveryThreeMinutes() {
//        log.info("Cache is deleted")
//    }
//}