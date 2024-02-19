package com.sparta.musuknyu.domain.dummy.controller

import com.sparta.musuknyu.domain.dummy.service.DummyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "dummy", description = "더미")
@RequestMapping("/dummy")
@RestController
class DummyController(
    private val dummyService: DummyService
) {

    @Operation(summary = "상품 더미 생성")
    @PostMapping("/items")
    fun generateDummyPosts(
        @RequestParam count: Int
    ): ResponseEntity<String> {
        // 여기에서 더미 데이터를 생성하는 로직을 호출
        dummyService.addDummyItems(count)
        return ResponseEntity.status(HttpStatus.CREATED).body("${count} Dummy items added successfully.")
    }
}