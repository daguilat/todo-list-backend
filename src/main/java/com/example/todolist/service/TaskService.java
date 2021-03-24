package com.example.todolist.service;

import java.util.List;
import java.util.Map;

import com.example.todolist.model.Task;

public interface TaskService {
    
    List<Task> getAllTask();

    Task saveTask(Task task, Integer user_id);

    Map<String, String> deleteTask(Integer task_id, Integer user_id);
}
