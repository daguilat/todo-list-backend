package com.example.todolist.service;

import java.util.List;

import com.example.todolist.model.Audit;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;

public interface AuditService {
    
    List<Audit> getAllAudit();

    Audit saveAudit(User user, Task task, String activity);
}
