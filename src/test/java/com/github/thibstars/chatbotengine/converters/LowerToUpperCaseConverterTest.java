package com.github.thibstars.chatbotengine.converters;

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