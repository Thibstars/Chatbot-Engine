package com.github.thibstars.chatbotengine.auth;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Thibault Helsmoortel
 */
@Data
@Slf4j
public class TokenAuthentication implements Authentication {

    private String token;
    private TokenAuthenticationHandler handler;

    @Override
    public void authenticate() {
        log.info("Authenticating with token...");
        if (StringUtils.isNotBlank(token)) {
            handler.authenticate(token);
        }

        throw new AuthenticationException("Authentication token was empty.");
    }
}
