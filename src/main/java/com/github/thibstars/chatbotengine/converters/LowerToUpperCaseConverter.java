package com.github.thibstars.chatbotengine.converters;

import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine.ITypeConverter;

/**
 * Converter for lower- to uppercase Strings
 *
 * @author Thibault Helsmoortel
 */
public class LowerToUpperCaseConverter implements ITypeConverter<String> {

    @Override
    public String convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return value.toUpperCase();
        }

        return value;
    }
}
