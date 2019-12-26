package com.github.thibstars.chatbotengine.auth.dbl;

import com.github.thibstars.chatbotengine.auth.TokenAuthentication;
import org.springframework.stereotype.Component;

/**
 * @author Thibault Helsmoortel
 */
@Component
public class DblTokenAuthentication extends TokenAuthentication {

    public DblTokenAuthentication(DblTokenAuthenticationHandler dblTokenAuthenticationHandler) {
        setHandler(dblTokenAuthenticationHandler);
    }
}
