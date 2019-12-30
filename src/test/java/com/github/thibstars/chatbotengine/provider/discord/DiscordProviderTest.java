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

package com.github.thibstars.chatbotengine.provider.discord;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthentication;
import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
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
class DiscordProviderTest {

    private DiscordProvider provider;
    private DiscordTokenAuthentication authentication;

    @BeforeEach
    void setUp() {
        this.authentication = mock(DiscordTokenAuthentication.class);
        this.provider = new DiscordProvider(authentication);
    }

    @Test
    void shouldAuthenticate() {
        Assertions.assertDoesNotThrow(() -> provider.authenticate(), "Authentication should not throw exception.");
        Assertions.assertTrue(provider.isAuthenticated(), "Provider must be authenticated.");
        verify(authentication).authenticate();
        verifyNoMoreInteractions(authentication);
    }

    @Test
    void shouldNotAuthenticateWhenAlreadyAuthenticated() {
        provider.authenticate(); // Make sure the provider is already authenticated
        Assertions.assertTrue(provider.isAuthenticated(), "Provider must be authenticated.");
        Assertions.assertDoesNotThrow(() -> provider.authenticate(), "Authentication should not throw exception.");
        Assertions.assertTrue(provider.isAuthenticated(), "Provider must still be authenticated.");
        verify(authentication).authenticate();
        verifyNoMoreInteractions(authentication);
    }
}
