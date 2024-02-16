package com.sparta.musuknyu.infra.config

import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Configuration

//@Configuration
//class CacheConfig {
//    fun cacheManager(): CacheManager {
//        // configure and return an implementation of Spring's CacheManager SPI
//        val cacheManager = SimpleCacheManager()
//        cacheManager.setCaches(listOf(ConcurrentMapCache("default")))
//        return cacheManager
//    }
//}