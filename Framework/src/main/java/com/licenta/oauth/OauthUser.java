package com.licenta.oauth;

import org.scribe.model.OAuthRequest;
import org.scribe.oauth.OAuthService;

import java.io.Serializable;

/**
 *@author Lucian CONDESCU
 */
public class OauthUser implements Serializable {

    private transient OAuthService service;
    private transient OAuthRequest request;

    public OAuthService getService() {
        return service;
    }

    public void setService(OAuthService service) {
        this.service = service;
    }

    public OAuthRequest getRequest() {
        return request;
    }

    public void setRequest(OAuthRequest request) {
        this.request = request;
    }
}
