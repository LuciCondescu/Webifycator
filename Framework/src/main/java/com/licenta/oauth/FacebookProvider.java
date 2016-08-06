package com.licenta.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by Lucian CONDESCU
 */
public class FacebookProvider extends Provider {

    public FacebookProvider(String providerId, String providerSecret, String callbackUrl) {
        super(providerId,providerSecret,callbackUrl);
    }

    public OAuthService getOauthService(ServiceBuilder builder) {
        return builder.provider(FacebookApi.class)
                .apiKey(this.providerId)
                .apiSecret(this.providerSecret)
                .callback(this.callbackUrl)
                .scope("public_profile email")
                .build();
    }

    @Override
    public OAuthRequest getOauthRequest() {
        return new OAuthRequest(Verb.GET,"https://graph.facebook.com/me?fields=id,name,email");
    }

}
