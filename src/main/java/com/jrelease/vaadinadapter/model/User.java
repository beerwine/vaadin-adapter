package com.jrelease.vaadinadapter.model;

import java.util.EnumSet;
import java.util.Set;


public class User {
    private String id;
    private String name;
    private Set<AuthMethod> authenticationMethods = EnumSet.noneOf(AuthMethod.class);
    private Set<AuthMethod> authorizationMethods = EnumSet.noneOf(AuthMethod.class);
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }    
    
    public Set<AuthMethod> getAuthenticationMethods() {
        return authenticationMethods;
    }
    
    public void setAuthenticationMethods(Set<AuthMethod> authenticationMethods) {
        this.authenticationMethods = authenticationMethods;
    }
    
    public Set<AuthMethod> getAuthorizationMethods() {
        return authorizationMethods;
    }
    
    public void setAuthorizationMethods(Set<AuthMethod> authorizationMethods) {
        this.authorizationMethods = authorizationMethods;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

}
