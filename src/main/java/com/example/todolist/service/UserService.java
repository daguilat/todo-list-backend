package com.example.todolist.service;

import com.example.todolist.exceptions.Exception;
import com.example.todolist.model.User;

public interface UserService {
    
    public User validateUser(String username, String password) throws Exception;

    public User registerUser(User user) throws Exception;
}
