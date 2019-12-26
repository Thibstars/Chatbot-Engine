package com.github.thibstars.chatbotengine;

import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(SentenceGenerator.class)
class ChatbotEngineApplicationTests {

    @Test
    void contextLoads() {
        // No assertions needed, just checking application startup
    }

}
