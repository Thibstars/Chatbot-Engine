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

package com.github.thibstars.chatbotengine.cli.commands;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine.Help.ColorScheme;

/**
 * Class responsible for command execution.
 *
 * @author Thibault Helsmoortel
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class CommandExecutor {

    private final List<BaseCommand> baseCommands;
    private final PrintWriter printWriter;

    /**
     * Tries to execute a command.
     *
     * @param context the context to supply to the command
     * @param commandMessage the command message (stripped from its prefix)
     * @param notRecognizedCallback callback to run when the command wasn't recognized
     * @return true if the command was executed, false if otherwise
     */
    public boolean tryExecute(Object context, String commandMessage, Runnable notRecognizedCallback) {
        AtomicBoolean commandRecognised = new AtomicBoolean(false);

        if (StringUtils.isNotBlank(commandMessage)) {
            baseCommands.forEach(command -> {
                Command commandType = command.getClass().getAnnotation(Command.class);
                String commandName = commandType.name();

                if (commandMessage.split(" ")[0].equals(commandName)) {
                    commandRecognised.set(true);
                    command.setContext(context);

                    String args = commandMessage.substring(commandMessage.indexOf(commandType.name()) + commandType.name().length()).trim();

                    CommandLine commandLine = new CommandLine(command);
                    commandLine.setOut(printWriter);
                    commandLine.setErr(printWriter);
                    commandLine.setColorScheme(new ColorScheme.Builder().ansi(Ansi.OFF).build());

                    if (StringUtils.isNotBlank(args)) {
                        commandLine.execute(args.split(" "));
                    } else {
                        commandLine.execute();
                    }
                }
            });

            if (commandRecognised.get()) {
                log.debug("Executed command: {}.", commandMessage);
            } else {
                log.debug("Command not recognized: {}.", commandMessage);
                notRecognizedCallback.run();
            }
        }

        return commandRecognised.get();
    }
}
