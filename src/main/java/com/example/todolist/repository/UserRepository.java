package com.example.todolist.repository;

import java.util.Optional;

import com.example.todolist.exceptions.Exception;
import com.example.todolist.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT NEXTVAL('user_id_seq')", nativeQuery = true)
    int getUserId(); 

    @Query(value = "SELECT user_id, username, first_name, last_name, password FROM users WHERE username = ?1 AND password = ?2", nativeQuery = true)
    Optional<User> findByUsernameAndPassword(String username, String password) throws Exception;

    @Query(value = "SELECT user_id, username, first_name, last_name, password FROM users WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username) throws Exception;

    /* RETURN RANDOM USER BETWEEN THE ONES WITH THE LESS NUMBER OF TASKS
        SELECT *
        FROM (
            SELECT count(*) as count, users.*
            FROM users LEFT JOIN task ON users.user_id=task.user_id
            GROUP BY users.user_id
        ) as task_users_count
        WHERE task_users_count.count = (
                    SELECT MIN(count) 
                    FROM (SELECT users.user_id, count(*)
                    FROM users LEFT JOIN task ON users.user_id=task.user_id 
                    GROUP BY users.user_id) as task_count
                    )
        ORDER BY RANDOM()
        LIMIT 1; */
    @Query(value = "SELECT * FROM ( SELECT count(*) as count, users.* FROM users LEFT JOIN task ON users.user_id=task.user_id GROUP BY users.user_id ) as task_users_count WHERE task_users_count.count = ( SELECT MIN(count) FROM (SELECT users.user_id, count(*) FROM users LEFT JOIN task ON users.user_id=task.user_id GROUP BY users.user_id) as task_count ) ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
    Optional<User> findRandomLessTasksUser();
}

/*

// DEVUELVE TODOS LOS USUARIOS Y SUS TAREAS
SELECT users.*, count(*) FROM users LEFT JOIN task ON users.user_id=task.user_id GROUP BY users.user_id ORDER BY count ASC;

// DEVUELVE EL VALOR MÍNIMO
SELECT MIN(count) 
FROM (SELECT users.user_id, count(*) 
      FROM users LEFT JOIN task ON users.user_id=task.user_id 
      GROUP BY users.user_id) as count;

// DEVUELVE A LOS USUARIO CON MENOR TAREAS     
SELECT *
FROM (
    SELECT count(*) as count, users.*
    FROM users LEFT JOIN task ON users.user_id=task.user_id
    GROUP BY users.user_id
) as task_users_count
WHERE task_users_count.count = (
               SELECT MIN(count) 
               FROM (SELECT users.user_id, count(*)
               FROM users LEFT JOIN task ON users.user_id=task.user_id 
               GROUP BY users.user_id) as task_count
               )
*/