package com.jrelease.vaadinadapter.view.user.authmethod;

import com.jrelease.vaadinadapter.model.AuthMethod;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper class for {@link AuthMethod}
 */
public class AuthMethodHelper {
    private AuthMethodHelper() {

    }

    public static String getHumanizedName(AuthMethod method) {
        return StringUtils.capitalize(method.name().replaceAll("_", " ").toLowerCase());
    }

    public static List<String> getHumanizedNames(Collection<AuthMethod> methods) {
        List<String> names = new LinkedList<String>();
        for (AuthMethod method : methods) {
            names.add(getHumanizedName(method));
        }
        return names;
    }
}
