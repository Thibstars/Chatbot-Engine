package com.github.thibstars.chatbotengine.auth;

/**
 * @author Thibault Helsmoortel
 */
@FunctionalInterface
public interface TokenAuthenticationHandler {

    void authenticate(String token) throws AuthenticationException;

}
