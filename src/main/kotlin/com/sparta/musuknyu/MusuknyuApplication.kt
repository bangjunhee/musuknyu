package com.sparta.musuknyu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling

class MusuknyuApplication

fun main(args: Array<String>) {
	runApplication<MusuknyuApplication>(*args)
}
