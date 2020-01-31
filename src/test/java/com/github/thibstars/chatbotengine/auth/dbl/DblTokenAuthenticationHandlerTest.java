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

package com.github.thibstars.chatbotengine.auth.dbl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
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
