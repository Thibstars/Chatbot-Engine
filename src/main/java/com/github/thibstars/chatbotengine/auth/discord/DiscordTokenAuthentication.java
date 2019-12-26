package com.github.thibstars.chatbotengine.auth.discord;

import com.github.thibstars.chatbotengine.auth.TokenAuthentication;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Component
public class DiscordTokenAuthentication extends TokenAuthentication {

    public DiscordTokenAuthentication(DiscordTokenAuthenticationHandler discordTokenAuthenticationHandler) {
        setHandler(discordTokenAuthenticationHandler);
    }
}
