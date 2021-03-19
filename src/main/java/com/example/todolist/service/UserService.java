package com.example.todolist.service;

import com.example.todolist.exceptions.AuthException;
import com.example.todolist.model.User;

public interface UserService {
    
    public User validateUser(String username, String password) throws AuthException;

    public User registerUser(String username, String password, String first_name, String last_name) throws AuthException;
}
