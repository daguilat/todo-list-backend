package com.example.todolist.service;

import java.util.List;

import com.example.todolist.model.Task;

public interface TaskService {
    
    List<Task> getAllTask(int user_id);

    Task createTask(Task task, int user_id);

    Task updateTask(Task task, int user_id);

    void deleteTask(int task_id, int user_id);
}
