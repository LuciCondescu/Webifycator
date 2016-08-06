package org.scribe.builder.api;

import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Yahoo2Api extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "https://api.login.yahoo.com/oauth2/request_auth?response_type=code&client_id=%s&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.login.yahoo.com/oauth2/get_token";
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new AccessTokenExtractor() {

            public Token extract(String response) {
                Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");

                Matcher matcher = Pattern.compile("\"access_token\":\"([^&\"]+)\"").matcher(response);
                if (matcher.find()) {
                    String token = OAuthEncoder.decode(matcher.group(1));
                    return new Token(token, "", response);
                } else {
                    throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
                }
            }
        };
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        // Append scope if present
        if (config.hasScope()) {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(),
                    OAuthEncoder.encode(config.getCallback()),
                    OAuthEncoder.encode(config.getScope()));
        } else {
            return String.format(AUTHORIZE_URL, config.getApiKey(),
                    OAuthEncoder.encode(config.getCallback()));
        }
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public OAuthService createService(OAuthConfig config) {
        return new YahooOAuth2Service(this, config);
    }

    private class YahooOAuth2Service extends OAuth20ServiceImpl {

        private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
        private static final String GRANT_TYPE = "grant_type";
        private DefaultApi20 api;
        private OAuthConfig config;

        public YahooOAuth2Service(DefaultApi20 api, OAuthConfig config) {
            super(api, config);
            this.api = api;
            this.config = config;
        }

        @Override
        public Token getAccessToken(Token requestToken, Verifier verifier) {
            OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
            switch (api.getAccessTokenVerb()) {
                case POST:
                    request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
                    request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
                    request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
                    request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
                    request.addBodyParameter(GRANT_TYPE, GRANT_TYPE_AUTHORIZATION_CODE);
                    break;
                case GET:
                default:
                    request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
                    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
                    request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
                    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
                    if (config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
            }
            Response response = request.send();
            return api.getAccessTokenExtractor().extract(response.getBody());
        }
    }

    public static void main(String[] args) {
        Matcher matcher = Pattern.compile("\"access_token\":\"([^&\"]+)\"").matcher("{\"access_token\":\"KIN3UCac4h_kWcd.NBSc9ed5qePqHr4g0KGoc1DG42jUBK_y5qNYtsRD0f76QnxRYu3jURUNVEt_ZwBAjSYwdi2WBRSwOtruhL8njU2kE_Uuw.aVwdvdW7mrzrQU0Avylt1EYN3qEqojLiVMKCC52asVSTlB1tN6IqBde3hWnGAr3T4dO_El99k5mkppdmIW1TeVvHQBMZhvpG.XXkyntOnBwLC0c.XnQ3JAeWSISTcf8UEa1e1oWOeRQDSI969_nl5hXT1OmTSjqapa5D4HZqJ.0tIZTgTEj4v4wkF.bn9P9uSH82zbjccR8EnlXQJ3RlGnj49GLNkPMEaShWSKNzTOm8VUMTAyLorLgsFiucKtBWmc7WUngfKseLq_YBJxC48kwBFq7SuBI1ceZt1zGQiHro.zS2dbUxpl2wcU1Dl7xNlzu0JpCJ6vu37FpyHakYeyAt5kn3kcmwbGGxH7YWV6fwHd7se7UGnFcEesRYOVjOCuk7YoZK4SFV_RpCldMms.SKbvzbFbJbqxKtKNdA2Dw92zdUybT4gdT2wiaWeaTy5hd7So2yZ6tBZdmsUtFc6lNuSI4_ZusBJ5yP6wwYj2hua5qJYSKeXXUUcyO.m3ZnKrt8X1mAUJJ8TY1Q5D6lgIoRql9BVSZSO0q.APl6rlnd2DaOMS2JsMiREgv8vnQypFNt2DM7Z8iqgi3cxD27Itb.gw6uhBcRqO7Wx51qx91y3LAZruNzOVHAxyCu7phoscIpx.IyfeNuDocMFZPgjGqIxUAFoQKD1yDnrY5FadZOXJOJk43In7JJa5S7Bp6EmBVgXKYRLkSvTqDx1YNg_X.nNZf6eYL7yNM.JuOJRlhOUfTDYEk52t9HOLVD_U85CeK3YaGYJpgfzV7VNtsgPKH3Z0dcwc0POsEkDBfwM_XmybMbAKA5ND4g--\",\"refresh_token\":\"ALjgQlc8RBVSfF6s87aXUReJvIqD6OtktzNaGUBz9xyWnWD272Ro3A--\",\"expires_in\":3600,\"token_type\":\"bearer\",\"xoauth_yahoo_guid\":\"R3XXNF53CTZLWLPO4PZ3VPMNTE\"}");
        if (matcher.find()) {
            String token = OAuthEncoder.decode(matcher.group(1));
            System.out.println("token = " + token);

        }
    }
}

