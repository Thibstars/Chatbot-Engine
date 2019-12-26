package com.github.thibstars.chatbotengine.auth.dbl;

import com.github.thibstars.chatbotengine.auth.AuthenticationException;
import com.github.thibstars.chatbotengine.auth.TokenAuthenticationHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.discordbots.api.client.DiscordBotListAPI.Builder;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Slf4j
@Component
public class DblTokenAuthenticationHandler implements TokenAuthenticationHandler {

    @Setter
    private JDA jda;

    @Override
    public void authenticate(String token) {
        log.info("Authenticating with DBL...");
        try {
            new Builder()
                .token(token)
                .botId(jda.getSelfUser().getId())
                .build();
        } catch (Exception e) {
            throw new AuthenticationException("DBL token authentication failed.");
        }
    }
}
