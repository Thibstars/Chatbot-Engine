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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import picocli.CommandLine.Command;

/**
 * @author Thibault Helsmoortel
 */
@SpringBootTest
@DisplayNameGeneration(SentenceGenerator.class)
class CommandExecutorTest {

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private TestCommand<MessageReceivedEvent> testCommand;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @Mock
    private Runnable notRecognizedCallback;

    @Mock
    private Runnable failCallback;

    @BeforeEach
    public void beforeEach() {
        testCommand.reset();
    }

    @Test
    void shouldExecuteCommand() {
        String commandName = testCommand.getClass().getAnnotation(Command.class).name();

        int priorExecutions = testCommand.getExecutions();
        boolean executed = commandExecutor.tryExecute(messageReceivedEvent, commandName, notRecognizedCallback, failCallback);

        verifyNoInteractions(notRecognizedCallback);

        Assertions.assertEquals(messageReceivedEvent, testCommand.getContext());
        Assertions.assertEquals(priorExecutions + 1, testCommand.getExecutions());
        Assertions.assertTrue(executed, "Command should be executed.");
    }

    @Test
    void shouldExecuteCommandWithArguments() {
        String commandName = testCommand.getClass().getAnnotation(Command.class).name() + " -t";

        int priorExecutions = testCommand.getExecutions();
        boolean executed = commandExecutor.tryExecute(messageReceivedEvent, commandName, notRecognizedCallback, failCallback);

        verifyNoInteractions(notRecognizedCallback);

        Assertions.assertEquals(messageReceivedEvent, testCommand.getContext());
        Assertions.assertEquals(priorExecutions + 1, testCommand.getExecutions());
        Assertions.assertTrue(testCommand.isOption());
        Assertions.assertTrue(executed, "Command should be executed.");
    }

    @Test
    void shouldNotExecuteCommand() {
        String commandName = "someUnavailableCommand";

        boolean executed = commandExecutor.tryExecute(messageReceivedEvent, commandName, notRecognizedCallback, failCallback);

        // The executor should trigger a call to the notRecognizedCallback
        verify(notRecognizedCallback).run();

        Assertions.assertFalse(executed, "Command should not be executed.");
    }

    @Test
    void shouldHandleFailure() {
        String commandName = "someUnavailableCommand";

        boolean executed = commandExecutor.tryExecute(messageReceivedEvent, commandName, () -> {
            throw new IllegalArgumentException("Test Exception!");
        }, failCallback);

        verify(failCallback).run();

        Assertions.assertFalse(executed, "Command should not be executed.");
    }
}
