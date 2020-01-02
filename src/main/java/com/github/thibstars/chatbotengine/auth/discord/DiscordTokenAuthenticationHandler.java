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

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.auth.TokenAuthenticationHandler;
import javax.security.auth.login.LoginException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Slf4j
@Data
@Component
public class DiscordTokenAuthenticationHandler implements TokenAuthenticationHandler {

    private JDABuilder jdaBuilder;

    public DiscordTokenAuthenticationHandler() {
        this(new JDABuilder(AccountType.BOT));
    }

    public DiscordTokenAuthenticationHandler(JDABuilder jdaBuilder) {
        this.jdaBuilder = jdaBuilder;
    }

    @Override
    public void authenticate(String token) {
        log.info("Authenticating with Discord...");
        try {
            jdaBuilder
                .setToken(token)
                .build()
                .awaitReady();
        } catch (LoginException e) {
            throw new AuthenticationException("Discord token authentication failed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage(), e);
        }
    }
}
