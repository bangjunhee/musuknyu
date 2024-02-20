package com.sparta.musuknyu.domain.searchHistoryRedis.controller

import com.sparta.musuknyu.common.SortOrder
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.entity.ItemTag
import com.sparta.musuknyu.domain.searchHistory.dto.KeywordResponseDto
import com.sparta.musuknyu.domain.searchHistory.service.SearchServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "search", description = "검색")
@RequestMapping("/v2/search")
@RestController
class RedisSearchController(
    private val searchService: SearchServiceImpl
) {
    @Operation(summary = "인기 검색어 조회")
    @GetMapping("/popularKeywords")
    fun getPopularKeywords(): ResponseEntity<List<KeywordResponseDto>> {
        val keywordList = searchService.getPopularKeywords()
        return ResponseEntity.ok(keywordList)
    }

    @Operation(summary = "상품 검색")
    @GetMapping
    fun searchItemList(
        @RequestParam search:String
    ): ResponseEntity<List<ItemResponseDto>> {

        val searchedItem = searchService.searchItem(search)
        return ResponseEntity.ok(searchedItem)
    }
    @Operation(summary = "상품 검색(페이징 + 정렬)")
    @GetMapping("/alignment")
    fun getPostListPaginated(
        @RequestParam(defaultValue = "0") page: Int,
        sortOrder: SortOrder?,
        itemTag: ItemTag,
        keyWords: String?
    ): ResponseEntity<Page<ItemResponseDto>> {
        searchService.countKeywords(keyWords)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(searchService.getItemListPaginated(page, sortOrder, itemTag, keyWords))
    }
}