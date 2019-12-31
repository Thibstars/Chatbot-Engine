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

package com.github.thibstars.chatbotengine.cli.converters;

import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Thibault Helsmoortel
 */
@SpringBootTest
@DisplayNameGeneration(SentenceGenerator.class)
class LowerToUpperCaseConverterTest {

    private LowerToUpperCaseConverter lowerToUpperCaseConverter;

    @BeforeEach
    void setUp() {
        this.lowerToUpperCaseConverter = new LowerToUpperCaseConverter();
    }

    @DisplayName("Should convert lower case String.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "alllowercase", "ALLUPPERCASE", "mixedVALUE", "v4lu3"})
    void shouldConvertLowerCaseString(String inputToConvert) {
        String converted = lowerToUpperCaseConverter.convert(inputToConvert);

        if (StringUtils.isNotBlank(inputToConvert)) {
            Assertions.assertTrue(StringUtils.isNotBlank(converted) && converted.equalsIgnoreCase(inputToConvert), "String should be converted correctly.");
        } else {
            Assertions.assertEquals(inputToConvert, converted, "Null, empty or blank input must remain the same.");
        }
    }

}