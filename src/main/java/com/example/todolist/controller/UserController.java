package com.example.todolist.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.todolist.Constants;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin
@RestController
@RequestMapping("/todolist/user")
public class UserController{

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUser(HttpServletRequest httpServletRequest){
        return new ResponseEntity<List<User>>(userService.getAllUser(), HttpStatus.OK);
    }

    //Controller for users to login
    //Input: the user data
    //Output: if the user logged in correctly it returns the bearer token, if not, an error message
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user){
        Map<String, String> result = userService.validateUser(user.getUsername(), user.getPassword());
        if(!result.get("status").equals("200"))
            return new ResponseEntity<>(result, HttpStatus.OK);
        
        User validated_user = userService.getUserByUsername(user.getUsername());
        return new ResponseEntity<>(generateJWTTToken(validated_user), HttpStatus.OK);
    }

    //Controller for users to register
    //Input: the user data
    //Output: if the user registered correctly it returns the bearer token, if not, an error message
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user){ 
        userService.registerUser(user);
        return new ResponseEntity<>(generateJWTTToken(user), HttpStatus.OK);
    }

    //Method to generate the bearer token for logged or registered users
    //Input: the user data
    //Output: the bearer token
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
        map.put("status", "200");
        map.put("token", token);
        map.put("user_id", String.valueOf(user.getUser_id()));
        return map;
    }
}