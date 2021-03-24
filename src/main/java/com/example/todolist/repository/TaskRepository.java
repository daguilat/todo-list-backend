package com.example.todolist.repository;

import java.util.List;

import com.example.todolist.model.Task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    
    //Query to get the next task_id in the sequence
    //Output: The next task id
    @Query(value = "SELECT NEXTVAL('task_id_seq')", nativeQuery = true)
    Integer getTaskId(); 

    //Query to get the list of tasks by task_id descending order
    //Output: the task list
    @Query(value = "SELECT * FROM task ORDER BY task_id DESC", nativeQuery = true)
    List<Task> getAllTasks();
}
