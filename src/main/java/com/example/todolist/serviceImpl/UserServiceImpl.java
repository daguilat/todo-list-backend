package com.example.todolist.serviceImpl;

import com.example.todolist.exceptions.AuthException;
import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.UserService;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    // @Autowired
    // AuditRepository auditRepository;
    
    @Override
    public User validateUser(String username, String password) throws AuthException {
        if(username != null)
            username.toLowerCase();
        try{
            User user = userRepository.findByUsername(username);
            if(user == null)
                throw new AuthException("Invalid username.");
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new AuthException("Invalid password.");
            
            //Registering user login
            //Integer audit = auditRepository.create(username, null, new Date(), "User logged in");
            return user;
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public User registerUser(String username, String password, String first_name, String last_name)
            throws AuthException {
        //Setting username to lowercase
        if(username != null) 
            username.toLowerCase();
        
        //Verifying user existence
        User user = userRepository.findByUsername(username);
        if(user != null){
            throw new AuthException("Failed to create user. Username already exists.");
        }
        
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        int id = userRepository.getUserId();
        //Creating user
        User new_user =  userRepository.save(new User(id, username, hashedPassword, first_name, last_name));
        //Registering user creation
        //Integer audit = auditRepository.create(username, null, new Date(), "User creation");
        
        return new_user;
    }
    
}
