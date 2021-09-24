package com.example.personalBlog.dao;

import com.example.personalBlog.model.User;

import java.util.Set;

public interface UserDao {
    public Set<User> getAllUsers();

    public User getUserById(int id);

    public int deleteUserById(int id);
}
