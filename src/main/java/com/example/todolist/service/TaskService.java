package com.example.todolist.service;

import java.util.List;

import com.example.todolist.model.Task;

public interface TaskService {
    
    List<Task> getAllTask(int user_id) throws Exception;

    Task createTask(Task task) throws Exception;

    Task updateTask(Task task) throws Exception;

    void deleteTask(int task_id) throws Exception;
}
