package com.example.todolist.serviceImpl;

import java.util.Date;
import java.util.Optional;

import com.example.todolist.exceptions.Exception;
import com.example.todolist.model.Audit;
import com.example.todolist.model.User;
import com.example.todolist.repository.AuditRepository;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.UserService;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditRepository auditRepository;
    
    @Override
    public User validateUser(String username, String password) throws Exception {
        if(username != null)
            username.toLowerCase();
        try{
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isEmpty())
                throw new Exception("Invalid username.");
            
            if(!BCrypt.checkpw(password, user.get().getPassword()))
                throw new Exception("Invalid password.");
            
            //Registering user login
            int audit_id = auditRepository.getAuditId();
            Audit audit = new Audit(audit_id, user.get(), null, "User logged in", new Date());
            auditRepository.save(audit);

            return user.get();
        } catch (Exception e){

        }
        return null;
    }
    
    @Override
    public User registerUser(User user)
            throws Exception {
        //Setting username to lowercase
        if(user.getUsername() != null) 
            user.setUsername(user.getUsername().toLowerCase());
        
        //Verifying user existence
        Optional<User> verified_user = userRepository.findByUsername(user.getUsername());
        if(verified_user.isPresent()){
            throw new Exception("Failed to create user. Username already exists.");
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
        int audit_id = auditRepository.getAuditId();
        Audit audit = new Audit(audit_id, new_user, null, "User created.", new Date());
        auditRepository.save(audit);
        
        return new_user;
    }
    
}
