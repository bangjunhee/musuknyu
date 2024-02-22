package com.sparta.musuknyu.common.schedule

import com.sparta.musuknyu.domain.searchHistory.entity.SearchHistoryEntity
import com.sparta.musuknyu.domain.searchHistory.repository.SearchHistoryRepository
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


@Service
class SchedulerService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val searchHistoryRepository: SearchHistoryRepository,
) {

    @Scheduled(fixedRate = 30000) // 30초마다 실행
    fun updateCacheAndDatabase() {
        val keywordEntries = redisTemplate.opsForZSet().rangeWithScores("키워드 랭킹", 0, -1)
        keywordEntries?.forEach { entry ->
            val keyword = entry.value as String
            val score = entry.score
            val searchHistoryEntity = searchHistoryRepository.findByKeywords(keyword)
                ?: SearchHistoryEntity(keywords = keyword, score = score)
            log.info("Cache data saved to database $keyword $score")
            searchHistoryEntity.score = score
            searchHistoryRepository.save(searchHistoryEntity)
        }
        redisTemplate.delete("키워드 랭킹")


    }
}

