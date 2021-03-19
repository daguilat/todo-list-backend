package com.example.todolist.repository;

import com.example.todolist.exceptions.AuthException;
import com.example.todolist.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT NEXTVAL('user_id_seq')", nativeQuery = true)
    int getUserId(); 

    @Query(value = "SELECT username, first_name, last_name, password FROM users WHERE username = ?1 AND password = ?2", nativeQuery = true)
    User findByUsernameAndPassword(String username, String password) throws AuthException;

    @Query(value = "SELECT username, first_name, last_name, password FROM users WHERE username = ?1", nativeQuery = true)
    User findByUsername(String username) throws AuthException;
}
