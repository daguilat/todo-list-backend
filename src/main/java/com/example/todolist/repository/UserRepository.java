package com.example.todolist.repository;

import java.util.Optional;

import com.example.todolist.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    //Query to get the next user_id in the sequence
    //Output: The next user id
    @Query(value = "SELECT NEXTVAL('user_id_seq')", nativeQuery = true)
    Integer getUserId(); 

    //Query to get the user by username and password
    //Output: The user asocciated to the username and password
    @Query(value = "SELECT user_id, username, first_name, last_name, password FROM users WHERE username = ?1 AND password = ?2", nativeQuery = true)
    Optional<User> findByUsernameAndPassword(String username, String password);

    //Query to get the user by username
    //Output: The user asocciated to the username
    @Query(value = "SELECT user_id, username, first_name, last_name, password FROM users WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);

    /* RETURNS RANDOM USER WITHOUT TASKS

        SELECT users.* 
        FROM users 
        LEFT JOIN task ON users.user_id = task.user_id
        WHERE task.user_id IS NULL
        ORDER BY RANDOM()
        LIMIT 1;
    */
    @Query(value = "SELECT users.* FROM users LEFT JOIN task ON users.user_id = task.user_id WHERE task.user_id IS NULL ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<User> findRandomUserWithoutTasks();

    /* RETURNS RANDOM USER WITH THE LESS NUMBER OF TASKS

        SELECT *
        FROM (
            SELECT count(*) as count, users.*
            FROM users INNER JOIN task ON users.user_id=task.user_id 
            GROUP BY users.user_id
        ) as task_users_count
        WHERE task_users_count.count = (
                    SELECT MIN(count) 
                    FROM (SELECT users.user_id, count(*)
                    FROM users INNER JOIN task ON users.user_id=task.user_id 
                    GROUP BY users.user_id) as task_count
                    )
        ORDER BY RANDOM()
        LIMIT 1; 
    */
    @Query(value = "SELECT * FROM ( SELECT count(*) as count, users.* FROM users INNER JOIN task ON users.user_id=task.user_id GROUP BY users.user_id ) as task_users_count WHERE task_users_count.count = ( SELECT MIN(count) FROM (SELECT users.user_id, count(*) FROM users INNER JOIN task ON users.user_id=task.user_id GROUP BY users.user_id) as task_count ) ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<User> findRandomUserWithoutLessAmountOfTasks();
}