package com.example.todolist.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.AuditService;
import com.example.todolist.service.UserService;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditService auditService;


    @Override
    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent())
            return null;
        
        return user.get();
    }
    
    @Override
    public Map<String, String> validateUser(String username, String password){      
        Map<String, String> result = new HashMap<>();  
        if(username.equals("") || username == null){
            result.put("status", "400");
            result.put("message", "No username given");
            return result;
        }
        if(password.equals("") || password == null){
            result.put("status", "400");
            result.put("message", "No password given");
            return result;
        }
        
        username.toLowerCase();    
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            result.put("status", "400");
            result.put("message", "Invalid username.");
            return result;
        }
        
        if(!BCrypt.checkpw(password, user.get().getPassword())){
            result.put("status", "400");
            result.put("message", "Invalid password.");
            return result;
        }
        
        //Registering user login
        auditService.saveAudit(user.get(), null, "User logged in");
        
        result.put("status", "200");
        result.put("message", "OK");
        return result;
    }
    
    @Override
    public Map<String, String> registerUser(User user){     
        Map<String, String> result = new HashMap<>();  
        if(user.getUsername() == null){
            result.put("status", "400");
            result.put("message", "No username given");
            return result;
        }
        if(user.getPassword() == null){
            result.put("status", "400");
            result.put("message", "No password given");
            return result;
        } 

        //Setting username to lowercase
        if(user.getUsername() != null) 
            user.setUsername(user.getUsername().toLowerCase());
        
        //Verifying user existence
        Optional<User> verified_user = userRepository.findByUsername(user.getUsername());
        if(verified_user.isPresent()){
            result.put("status", "400");
            result.put("message", "Failed to create user. Username already exists.");
            return result;

        }
        
        // Encrypting password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);

        // Generating user_id
        int user_id = userRepository.getUserId();
        user.setUser_id(user_id);

        //Creating user
        User new_user =  userRepository.save(user);

        //Registering user creation
        auditService.saveAudit(new_user, null, "User created.");
        
        result.put("status", "200");
        return result;
    }
    
}
