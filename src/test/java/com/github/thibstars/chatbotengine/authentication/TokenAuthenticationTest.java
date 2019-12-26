package com.github.thibstars.chatbotengine.authentication;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.auth.TokenAuthentication;
import com.github.thibstars.chatbotengine.auth.TokenAuthenticationHandler;
import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
class TokenAuthenticationTest {

    private TokenAuthentication tokenAuthentication;
    private TokenAuthenticationHandler handler;

    @BeforeEach
    void setUp() {
        this.handler = mock(TokenAuthenticationHandler.class);
        this.tokenAuthentication = new TokenAuthentication();
        tokenAuthentication.setHandler(handler);
    }

    @Test
    void shouldAuthenticate() {
        String token = RandomStringUtils.random(10);
        tokenAuthentication.setToken(token);
        Assertions.assertDoesNotThrow(() -> tokenAuthentication.authenticate(), "Authentication must not throw an exception.");
        verify(handler).authenticate(eq(token));
        verifyNoMoreInteractions(handler);
    }

    @Test
    void shouldNotAuthenticateWithEmptyToken() {
        tokenAuthentication.setToken(StringUtils.EMPTY);
        Assertions
            .assertThrows(AuthenticationException.class, () -> tokenAuthentication.authenticate(), "Authentication must fail with an empty token.");
        verifyNoInteractions(handler);
    }
}
