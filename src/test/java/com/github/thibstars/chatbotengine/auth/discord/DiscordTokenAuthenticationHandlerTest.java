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

package com.github.thibstars.chatbotengine.auth.discord;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
class DiscordTokenAuthenticationHandlerTest {

    private DiscordTokenAuthenticationHandler handler;
    private JDABuilder jdaBuilder;

    @BeforeEach
    void setUp() throws LoginException, InterruptedException {
        this.jdaBuilder = mock(JDABuilder.class);
        when(jdaBuilder.setToken(anyString())).thenReturn(jdaBuilder);
        JDA jda = mock(JDA.class);
        when(jdaBuilder.build()).thenReturn(jda);
        when(jda.awaitReady()).thenReturn(jda);
        this.handler = new DiscordTokenAuthenticationHandler(jdaBuilder);
    }

    @Test
    void shouldAuthenticate() {
        Assertions.assertDoesNotThrow(() -> handler.authenticate(RandomStringUtils.randomAscii(10)), "Authentication should not throw exception.");
    }

    @Test
    void shouldNotAuthenticateWithLoginException() throws LoginException {
        when(jdaBuilder.build()).thenThrow(new LoginException("Test Exception!"));
        Assertions.assertThrows(AuthenticationException.class, () -> handler.authenticate(RandomStringUtils.randomAscii(10)), "Authentication should throw exception.");
    }

    @Test
    void shouldReinterruptThread() throws LoginException, InterruptedException {
        JDA jda = mock(JDA.class);
        when(jdaBuilder.build()).thenReturn(jda);
        when(jda.awaitReady()).thenThrow(InterruptedException.class);
        handler.authenticate(RandomStringUtils.randomAscii(10));
        Assertions.assertTrue(Thread.currentThread().isInterrupted(), "Current thread should be reinterrupted.");
    }
}
