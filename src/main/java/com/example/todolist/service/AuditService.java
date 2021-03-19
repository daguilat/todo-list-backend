package com.example.todolist.service;

import java.util.List;

import com.example.todolist.model.Audit;

public interface AuditService {
    
    List<Audit> getAllAudit();

    Audit saveAudit(Audit audit);
}
