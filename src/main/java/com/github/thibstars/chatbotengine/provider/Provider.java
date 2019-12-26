package com.github.thibstars.chatbotengine.provider;

/**
 * @author Thibault Helsmoortel
 */
public abstract class Provider {

    protected boolean isAuthenticated;

    public Provider() {
        this.isAuthenticated = false;
    }

    public abstract void authenticate();

    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }
}
