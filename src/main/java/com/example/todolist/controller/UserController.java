package com.example.todolist.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.todolist.Constants;
import com.example.todolist.exceptions.Exception;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/todolist/user")
public class UserController{

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        
        if(username == null || password == null)
            throw new Exception("No username or password was found.");

        User user = userService.validateUser(username, password);
        
        return new ResponseEntity<>(generateJWTTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user){
        if(user.getUsername() == null || user.getPassword() == null)
            throw new Exception("No username or password was found.");
            
        userService.registerUser(user);
        return new ResponseEntity<>(generateJWTTToken(user), HttpStatus.OK);
    }

    public Map<String,String> generateJWTTToken(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                    .setIssuedAt(new Date(timestamp))
                    .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                    .claim("user_id", user.getUser_id())
                    .claim("first_name", user.getFirst_name())
                    .claim("last_name", user.getLast_name())
                    .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}