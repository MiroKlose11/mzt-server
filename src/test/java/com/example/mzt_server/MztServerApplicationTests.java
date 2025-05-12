package com.example.mzt_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class MztServerApplicationTests {

	@Test
	void contextLoads() {
		// 仅测试Spring上下文是否能加载
	}

} 