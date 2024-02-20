package com.sparta.musuknyu.infra.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

//@Configuration
class CacheConfig {

//    @Bean
    fun caffeineCacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager()
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(10, TimeUnit.MINUTES))
        return cacheManager
    }
}