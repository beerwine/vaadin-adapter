package com.jrelease.vaadinadapter.view.user.list.mvp;

import com.jrelease.vaadinadapter.model.User;

import java.util.List;


public class UserListModel {
    private List<User> users;

    
    public List<User> getUsers() {
        return users;
    }

    
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
