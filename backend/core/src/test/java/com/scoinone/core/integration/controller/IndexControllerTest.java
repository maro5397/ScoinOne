package com.scoinone.core.integration.controller;

import com.scoinone.core.config.TestContainerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class})
class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
}