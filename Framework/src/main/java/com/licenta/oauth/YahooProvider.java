package com.licenta.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Yahoo2Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/** This provider is not used because Yahoo does not provide the email address in the response. Why ? Nobody knows.
 *
 * Created by Lucian CONDESCU
 */
public class YahooProvider extends Provider {
    public YahooProvider(String apiID, String apiSecret, String callback) {
        super(apiID,apiSecret,callback);
    }

    @Override
    public OAuthService getOauthService(ServiceBuilder builder) {
        return builder.provider(Yahoo2Api.class)
                .apiKey(super.providerId)
                .apiSecret(super.providerSecret)
                .callback(super.callbackUrl)
                .build();
    }

    @Override
    public OAuthRequest getOauthRequest() {
        return new OAuthRequest(Verb.GET,"https://social.yahooapis.com/v1/user/GUID/profile?format=json");
    }
}
