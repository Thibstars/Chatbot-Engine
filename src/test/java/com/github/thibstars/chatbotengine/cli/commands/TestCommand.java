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

import lombok.Getter;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author Thibault Helsmoortel
 */
@Component
@Command(name = "testCommand", description = "Just some test command.")
public class TestCommand<C> extends BaseCommand<C, Integer> {

    @Getter
    @Option(names = {"-t", "--test"}, description = "Just some test option.")
    private boolean option;

    @Getter
    private int executions;

    public TestCommand() {
        this.executions = 0;
    }

    @Override
    public Integer call() {
        return executions++;
    }

    public void reset() {
        this.executions = 0;
        this.option = false;
    }
}
