package com.github.thibstars.chatbotengine.authentication.discord;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthenticationHandler;
import com.github.thibstars.chatbotengine.testutils.SentenceGenerator;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Thibault Helsmoortel
 */
@SpringBootTest
@DisplayNameGeneration(SentenceGenerator.class)
public class DiscordTokenAuthenticationHandlerTest {

    private DiscordTokenAuthenticationHandler handler;
    private JDABuilder jdaBuilder;

    @BeforeEach
    void setUp() throws LoginException, InterruptedException {
        this.jdaBuilder = mock(JDABuilder.class);
        when(jdaBuilder.setToken(anyString())).thenReturn(jdaBuilder);
        JDA jda = mock(JDA.class);
        when(jdaBuilder.build()).thenReturn(jda);
        when(jda.awaitReady()).thenReturn(jda);
        this.handler = new DiscordTokenAuthenticationHandler(jdaBuilder);
    }

    @Test
    void shouldAuthenticate() {
        Assertions.assertDoesNotThrow(() -> handler.authenticate(RandomStringUtils.randomAscii(10)), "Authentication should not throw exception.");
    }

    @Test
    void shouldNotAuthenticateWithLoginException() throws LoginException {
        when(jdaBuilder.build()).thenThrow(new LoginException("Test Exception!"));
        Assertions.assertThrows(AuthenticationException.class, () -> handler.authenticate(RandomStringUtils.randomAscii(10)), "Authentication should throw exception.");
    }
}
