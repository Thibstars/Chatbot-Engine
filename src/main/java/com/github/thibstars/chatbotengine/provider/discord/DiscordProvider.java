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

import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthentication;
import com.github.thibstars.chatbotengine.provider.Provider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Component
@Slf4j
@AllArgsConstructor
public class DiscordProvider extends Provider {

    private final DiscordTokenAuthentication authentication;

    @Override
    public void authenticate() {
        if (!isAuthenticated) {
            authentication.authenticate();
            isAuthenticated = true;
        } else {
            log.warn("{} was already authenticated.", this.getClass().getSimpleName());
        }
    }
}
