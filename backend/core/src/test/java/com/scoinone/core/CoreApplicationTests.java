package com.scoinone.core;

import com.scoinone.core.config.TestContainerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import({TestContainerConfig.class})
@ActiveProfiles("dev")
class CoreApplicationTests {

	@Test
	void contextLoads() {
	}

}
