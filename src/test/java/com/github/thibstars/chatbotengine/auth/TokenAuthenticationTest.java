/*
 * Copyright (c) 2019 Thibault Helsmoortel.
 *
 * This file is part of Chatbot Engine.
 *
 * Chatbot Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Chatbot Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Chatbot Engine.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.thibstars.chatbotengine.auth;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
