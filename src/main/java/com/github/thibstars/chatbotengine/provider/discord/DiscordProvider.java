package com.github.thibstars.chatbotengine.provider.discord;

import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthentication;
import com.github.thibstars.chatbotengine.provider.Provider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Component
@Slf4j
@AllArgsConstructor
public class DiscordProvider extends Provider {

    private final DiscordTokenAuthentication authentication;

    @Override
    public void authenticate() {
        if (!isAuthenticated) {
            authentication.authenticate();
            isAuthenticated = true;
        } else {
            log.warn("{} was already authenticated.", this.getClass().getSimpleName());
        }
    }
}
