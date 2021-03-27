package com.codecool.barbershop.barbershop.security.oauth2.user;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
/*
Every OAuth2 provider returns a different JSON response when we fetch
the authenticated user’s details. Spring security
parses the response in the form of a generic map of key-value pairs.
 */