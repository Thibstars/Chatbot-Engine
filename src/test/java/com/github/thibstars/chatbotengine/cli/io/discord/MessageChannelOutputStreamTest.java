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

package com.github.thibstars.chatbotengine.cli.io.discord;

import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.junit.jupiter.params.ParameterizedTest.INDEX_PLACEHOLDER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import java.util.stream.Stream;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Thibault Helsmoortel
 */
@SpringBootTest
@DisplayNameGeneration(SentenceGenerator.class)
class MessageChannelOutputStreamTest {

    private MessageChannelOutputStream messageChannelOutputStream;

    @Mock
    private MessageChannel messageChannel;

    @BeforeEach
    void setUp() {
        messageChannelOutputStream = new MessageChannelOutputStream();
        messageChannelOutputStream.setMessageChannel(messageChannel);

        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void shouldWriteMessageToChannel() {
        String message = "Hello World!";
        messageChannelOutputStream.write(message.getBytes(), 0, message.length());

        verify(messageChannel).sendMessage(message);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void shouldWriteCharCodeToChannel() {
        String message = "a";
        int charCode = message.toCharArray()[0];
        messageChannelOutputStream.write(charCode);

        verify(messageChannel).sendMessage(message);
    }

    @ParameterizedTest(name = INDEX_PLACEHOLDER + ": " + ARGUMENTS_PLACEHOLDER)
    @MethodSource("blankStrings")
    void shouldNotWriteBlankMessageToChannel(String message) {
        messageChannelOutputStream.write(message.getBytes(), 0, message.length());

        verifyNoMoreInteractions(messageChannel);
    }

    static Stream<String> blankStrings() {
        return Stream.of("", "   ", " ");
    }
}