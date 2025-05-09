package com.example.cms_mzt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class CmsMztApplicationTests {

	@Test
	void contextLoads() {
		// 仅测试Spring上下文是否能加载
	}

}
