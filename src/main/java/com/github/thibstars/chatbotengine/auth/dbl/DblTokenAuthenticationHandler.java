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

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.auth.TokenAuthenticationHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.discordbots.api.client.DiscordBotListAPI.Builder;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Slf4j
@Component
public class DblTokenAuthenticationHandler implements TokenAuthenticationHandler {

    @Setter
    private JDA jda;

    @Override
    public void authenticate(String token) {
        log.info("Authenticating with DBL...");
        try {
            new Builder()
                .token(token)
                .botId(jda.getSelfUser().getId())
                .build();
        } catch (Exception e) {
            throw new AuthenticationException("DBL token authentication failed.");
        }
    }
}
