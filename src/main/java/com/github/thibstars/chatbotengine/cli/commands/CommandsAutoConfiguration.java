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

package com.github.thibstars.chatbotengine.cli.commands;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

/**
 * @author Thibault Helsmoortel
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class CommandsAutoConfiguration {

    private final ListableBeanFactory listableBeanFactory;
    private final PrintWriter printWriter;

    @Bean
    public List<BaseCommand> commands() {
        Map<String, Object> commands = listableBeanFactory.getBeansWithAnnotation(CommandLine.Command.class);

        return commands.values().stream()
            .filter(command -> command instanceof BaseCommand)
            .map(command -> (BaseCommand) command)
            .collect(Collectors.toList());
    }

    @Bean
    @ConditionalOnMissingBean(CommandExecutor.class)
    public CommandExecutor commandExecutor() {
        List<BaseCommand> commands = commands();
        log.info("Registering command executor with {} commands.", commands.size());
        return new CommandExecutor(commands, printWriter);
    }
}
