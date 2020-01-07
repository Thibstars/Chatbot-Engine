/*
 * Copyright (c) 2020 Thibault Helsmoortel.
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

package com.github.thibstars.chatbotengine.cli.io.discord.config;

import com.github.thibstars.chatbotengine.cli.io.discord.MessageChannelOutputStream;
import java.io.PrintWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Thibault Helsmoortel
 */
@Configuration
public class DiscordCliAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(MessageChannelOutputStream.class)
    public MessageChannelOutputStream messageChannelOutputStream() {
        return new MessageChannelOutputStream();
    }

    @Bean
    @ConditionalOnMissingBean(PrintWriter.class)
    public PrintWriter discordPrintWriter() {
        return new PrintWriter(messageChannelOutputStream());
    }

}
