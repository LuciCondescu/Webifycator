package com.licenta.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.oauth.OAuthService;

/**
 * Created by Lucian CONDESCU
 */
public abstract class Provider {
    protected String providerId;
    protected String providerSecret;
    protected String callbackUrl;

    public Provider(String providerId, String providerSecret, String callbackUrl) {
        this.providerId = providerId;
        this.providerSecret = providerSecret;
        this.callbackUrl = callbackUrl;
    }

    public abstract OAuthService getOauthService(ServiceBuilder builder);

    public abstract OAuthRequest getOauthRequest();
}
