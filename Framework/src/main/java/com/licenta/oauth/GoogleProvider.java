package com.licenta.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Google2Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by Lucian CONDESCU
 */
public class GoogleProvider extends Provider {

    public GoogleProvider(String providerId, String providerSecret, String callbackUrl) {
        super(providerId,providerSecret,callbackUrl);
    }

    @Override
    public OAuthService getOauthService(ServiceBuilder builder) {
        return builder.provider(Google2Api.class)
                .apiKey(this.providerId)
                .apiSecret(this.providerSecret)
                .callback(this.callbackUrl)
                .scope("email profile")
                .build();
    }

    @Override
    public OAuthRequest getOauthRequest() {
        return new OAuthRequest(Verb.GET,"https://www.googleapis.com/oauth2/v2/userinfo");
    }

}
