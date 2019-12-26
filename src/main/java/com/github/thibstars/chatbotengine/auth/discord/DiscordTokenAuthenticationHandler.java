package com.github.thibstars.chatbotengine.auth.discord;

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.auth.TokenAuthenticationHandler;
import javax.security.auth.login.LoginException;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Slf4j
@Component
public class DiscordTokenAuthenticationHandler implements TokenAuthenticationHandler {

    private JDABuilder jdaBuilder;

    public DiscordTokenAuthenticationHandler() {
        this(new JDABuilder(AccountType.BOT));
    }

    public DiscordTokenAuthenticationHandler(JDABuilder jdaBuilder) {
        this.jdaBuilder = jdaBuilder;
    }

    @Override
    public void authenticate(String token) {
        log.info("Authenticating with Discord...");
        try {
            jdaBuilder
                .setToken(token)
                .build()
                .awaitReady();
        } catch (LoginException e) {
            throw new AuthenticationException("Discord token authentication failed.");
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
