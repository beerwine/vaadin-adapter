package com.jrelease.vaadinadapter.service;

import com.jrelease.vaadinadapter.model.AuthMethod;
import com.jrelease.vaadinadapter.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Mock implementation of {@link UserService}
 */
@Service
public class UserServiceImplMock implements UserService {
    // Mocked users
    private final Map<String, User> users = new HashMap<String, User>();
    
    public UserServiceImplMock() {
        // initialize mock data
        User user1 = new User();
        user1.setId("1");
        user1.setName("Alice");
        user1.getAuthenticationMethods().addAll(Arrays.asList(AuthMethod.USERNAME_PASSWORD, AuthMethod.SMS));
        user1.getAuthorizationMethods().addAll(Arrays.asList(AuthMethod.SMS, AuthMethod.CARD, AuthMethod.FINGERPRINT));
        saveUser(user1);
        
        User user2 = new User();
        user2.setId("2");
        user2.setName("Bob");
        user2.getAuthenticationMethods().addAll(Arrays.asList(AuthMethod.CERTIFICATE));
        user2.getAuthorizationMethods().addAll(Arrays.asList(AuthMethod.IRIS_RECOGNITION, AuthMethod.SMS, AuthMethod.FINGERPRINT));
        saveUser(user2);
    }
    
    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<User>(users.values());
    }
    
    
}
