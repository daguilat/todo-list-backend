package com.example.todolist.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.todolist.exceptions.AuthException;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/todolist/users")
public class UserController{

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        
        if(username == null || password == null)
            throw new AuthException("No username or password was found.");

        User user = userService.validateUser(username, password);

        Map<String, String> map = new HashMap<>();
        map.put("message", "user "+ user.getUsername() + " logged in successfully");
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap){
        String username = (String) userMap.get("username");
        String first_name = (String) userMap.get("first_name");
        String last_name = (String) userMap.get("last_name");
        String password = (String) userMap.get("password");
        
        if(username == null || password == null)
            throw new AuthException("No username or password was found.");
            
        userService.registerUser(username, password, first_name, last_name);

        Map<String, String> map = new HashMap<>();
        map.put("message", "User registered successfully");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}