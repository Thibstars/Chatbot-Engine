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

package com.github.thibstars.chatbotengine.cli;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Thibault Helsmoortel
 */
@SpringBootTest
@DisplayNameGeneration(SentenceGenerator.class)
class VersionProviderTest {

    private VersionProvider versionProvider;

    @BeforeEach
    void setUp() {
        this.versionProvider = new VersionProvider();
    }

    @DisplayName("Should return implementation version.")
    @Test
    void shouldReturnImplementationVersion() {
        String version = Arrays.toString(versionProvider.getVersion());

        Assertions.assertTrue(StringUtils.isNotBlank(version), "Version must not be blank.");
    }

    @DisplayName("Should return actual implementation version.")
    @Test
    void shouldReturnActualImplementationVersion() {
        Package pack = mock(Package.class);
        String version = "anActualVersion";
        when(pack.getImplementationVersion()).thenReturn(version);
        versionProvider.setPack(pack);

        String returnedVersion = Arrays.toString(versionProvider.getVersion());

        Assertions.assertTrue(StringUtils.isNotBlank(returnedVersion), "Version must not be blank.");
        Assertions.assertEquals(Arrays.toString(new String[]{version}), returnedVersion, "Versions must match.");
    }
}
