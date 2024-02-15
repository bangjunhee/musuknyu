package com.sparta.musuknyu.domain.item.controller

import com.sparta.musuknyu.domain.item.dto.ItemRequestDto
import com.sparta.musuknyu.domain.item.dto.ItemResponseDto
import com.sparta.musuknyu.domain.item.service.ItemService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "item", description = "상품")
@RequestMapping("/items")
@RestController
class ItemController(
    private val itemService: ItemService
){
    @Operation(summary = "상품 검색")
    @GetMapping("/search")
    fun searchItemList(
        @RequestParam search:String
    ): ResponseEntity<List<ItemResponseDto>> {
        val searchedItem = itemService.searchItem(search)
        return ResponseEntity.ok(searchedItem)
    }
    @Operation(summary = "상품 전체 조회")
    @GetMapping
    fun getItemList(): ResponseEntity<List<ItemResponseDto>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(itemService.getItemList())
    }

    @Operation(summary = "상품 단건 조회")
    @GetMapping("/{itemId}")
    fun getItemById(
        @PathVariable itemId: Long,
    ): ResponseEntity<ItemResponseDto>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(itemService.getItemById(itemId))
    }

    @Operation(summary = "상품 생성")
    @PostMapping
    fun addItem(@RequestBody itemRequestDto: ItemRequestDto
    ): ResponseEntity<ItemResponseDto>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(itemService.addItem(itemRequestDto))
    }

    @Operation(summary = "상품 수정")
    @PutMapping("/{itemId}")
    fun updateItem(
        @PathVariable itemId: Long,
        @RequestBody itemRequestDto: ItemRequestDto
    ): ResponseEntity<ItemResponseDto>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(itemService.updateItem(itemId, itemRequestDto))
    }

    @Operation(summary = "상품 삭제")
    @PatchMapping("/{itemId}")
    fun deleteItem(@PathVariable itemId: Long): ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(itemService.deleteItem(itemId))
    }
}