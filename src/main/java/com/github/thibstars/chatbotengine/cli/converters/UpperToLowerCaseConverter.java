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

package com.github.thibstars.chatbotengine.cli.converters;

import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine.ITypeConverter;

/**
 * Converter for upper- to lowercase Strings.
 *
 * @author Thibault Helsmoortel
 */
public class UpperToLowerCaseConverter implements ITypeConverter<String> {

    @Override
    public String convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return value.toLowerCase();
        }

        return value;
    }

}
