package com.github.thibstars.chatbotengine.authentication.dbl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.auth.dbl.DblTokenAuthenticationHandler;
import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.SelfUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Thibault Helsmoortel
 */
@SpringBootTest
@DisplayNameGeneration(SentenceGenerator.class)
class DblTokenAuthenticationHandlerTest {

    private DblTokenAuthenticationHandler handler;
    private SelfUser selfUser;

    @BeforeEach
    void setUp() {
        this.handler = new DblTokenAuthenticationHandler();
        JDA jda = mock(JDA.class);
        handler.setJda(jda);
        this.selfUser = mock(SelfUser.class);
        when(jda.getSelfUser()).thenReturn(selfUser);
        when(selfUser.getId()).thenReturn(RandomStringUtils.random(10));
    }

    @Test
    void shouldAuthenticate() {
        Assertions.assertDoesNotThrow(() -> handler.authenticate(RandomStringUtils.random(10)), "Authentication should not throw exception.");
    }

    @Test
    void shouldNotAuthenticateWithInvalidSelfUserId() {
        when(selfUser.getId()).thenReturn(null);
        Assertions.assertThrows(AuthenticationException.class, () -> handler.authenticate(RandomStringUtils.random(10)),
            "Authentication should throw exception.");
    }
}
