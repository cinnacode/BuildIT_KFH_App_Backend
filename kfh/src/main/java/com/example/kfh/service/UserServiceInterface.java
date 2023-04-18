package com.example.kfh.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserServiceInterface {
    public User getUserByName(String name);

    public List<User> getList();
}
