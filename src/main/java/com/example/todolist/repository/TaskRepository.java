package com.example.todolist.repository;

import com.example.todolist.model.Task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    
    @Query(value = "SELECT NEXTVAL('task_id_seq')", nativeQuery = true)
    int getTaskId(); 
}
