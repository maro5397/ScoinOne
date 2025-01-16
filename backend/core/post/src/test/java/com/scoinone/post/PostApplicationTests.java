package com.scoinone.post;

import com.scoinone.post.config.TestContainerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import({TestContainerConfig.class})
@ActiveProfiles("dev")
class PostApplicationTests {

	@Test
	@DisplayName("애플리케이션 컨텍스트 로딩 테스트 (실행 테스트)")
	void contextLoads() {
	}

}
