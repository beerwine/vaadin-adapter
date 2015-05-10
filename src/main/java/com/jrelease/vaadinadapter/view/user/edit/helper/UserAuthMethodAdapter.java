package com.jrelease.vaadinadapter.view.user.edit.helper;

import com.jrelease.vaadinadapter.model.AuthMethod;
import com.jrelease.vaadinadapter.model.User;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.CheckBox;


/**
 * Adapter object for editation of certain user's authentication and authorization method.
 * Provides checkboxes that allow setting the authentication and authorization method.
 */
public class UserAuthMethodAdapter {
    private static final String AUTHENTICATION = "authentication";
    private static final String AUTHORIZATION = "authorization";

    public static final String METHOD = "method";
    public static final String AUTHENTICATION_CHECKBOX = "authenticationCheckBox";
    public static final String AUTHORIZATION_CHECKBOX = "authorizationCheckBox";

    private final User user;
    private final AuthMethod method;
    private final BeanFieldGroup<UserAuthMethodAdapter> binder;
    private final CheckBox authenticationCheckBox = new CheckBox();
    private final CheckBox authorizationCheckBox = new CheckBox();

    /**
     * Creates new helper. Initializes binder.
     *
     * @param user user's data
     * @param method this method will be edited
     */
    public UserAuthMethodAdapter(final User user, final AuthMethod method) {
        this.user = user;
        this.method = method;
        
        binder = new BeanFieldGroup<UserAuthMethodAdapter>(UserAuthMethodAdapter.class);
        binder.setItemDataSource(this);
        
        binder.bind(authenticationCheckBox, AUTHENTICATION); 
        binder.bind(authorizationCheckBox, AUTHORIZATION);
    }

    /**
     * @return the authentication method to be set
     */
    public AuthMethod getMethod() {
        return method;
    }

    /**
     * @return binder of checkboxes. Used for commiting changes to {@link UserAuthMethodAdapter#user}
     */
    public BeanFieldGroup<UserAuthMethodAdapter> getBinder() {
        return binder;
    }

    /**
     * @return checkbox for setting authentication method
     */
    public CheckBox getAuthenticationCheckBox() {
        return authenticationCheckBox;
    }

    /**
     * @return checkbox for setting authorization method
     */
    public CheckBox getAuthorizationCheckBox() {
        return authorizationCheckBox;
    }

    public boolean getAuthentication() {
        return user.getAuthenticationMethods().contains(method);
    }

    public void setAuthentication(final boolean enabled) {
        if (!enabled) {
            user.getAuthenticationMethods().remove(method);
        } else {
            user.getAuthenticationMethods().add(method);
        }
    }

    public boolean getAuthorization() {
        return user.getAuthorizationMethods().contains(method);
    }

    public void setAuthorization(final boolean enabled) {
        if(!enabled) {
            user.getAuthorizationMethods().remove(method);
        } else{
            user.getAuthorizationMethods().add(method);
        }
    }
}
