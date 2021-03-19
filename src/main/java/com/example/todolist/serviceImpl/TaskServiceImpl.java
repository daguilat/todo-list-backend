package com.example.todolist.serviceImpl;

import com.example.todolist.repository.TaskRepository;
import com.example.todolist.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;
    
}
