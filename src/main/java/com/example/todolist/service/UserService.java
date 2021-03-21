package com.example.todolist.service;

import java.util.Map;

import com.example.todolist.model.User;

public interface UserService {
    
    public User getUserByUsername(String username);

    public Map<String, String> validateUser(String username, String password);

    public Map<String, String> registerUser(User user);
}
