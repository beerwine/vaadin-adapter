package com.jrelease.vaadinadapter.service;

import com.jrelease.vaadinadapter.model.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();
    User getUser(String id);
    void saveUser(User user);
}
