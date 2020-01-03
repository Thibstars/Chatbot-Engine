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

import java.io.OutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * OutputStream able to write to a set message channel.
 *
 * @author Thibault Helsmoortel
 */
@EqualsAndHashCode(callSuper = true)
@Component
@Data
public class MessageChannelOutputStream extends OutputStream {

    private MessageChannel messageChannel;

    @Override
    public void write(int b) {
        // It's tempting to use writer.write((char) b), but that may get the encoding wrong
        // This is inefficient, but it works
        write(new byte[] {(byte) b}, 0, 1);
    }

    @Override
    public void write(@NonNull byte[] b, int off, int len) {
        String content = new String(b, off, len);
        if (StringUtils.isNotBlank(content)) {
            messageChannel.sendMessage(content).queue();
        }
    }
}
