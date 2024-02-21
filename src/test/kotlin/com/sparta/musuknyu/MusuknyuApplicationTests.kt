package com.sparta.musuknyu

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class MusuknyuApplicationTests {

	@Test
	fun contextLoads() {
	}

}
