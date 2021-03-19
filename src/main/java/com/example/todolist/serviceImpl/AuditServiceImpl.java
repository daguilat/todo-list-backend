package com.example.todolist.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.example.todolist.model.Audit;
import com.example.todolist.repository.AuditRepository;
import com.example.todolist.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditRepository auditRepository;

    @Override
    public List<Audit> getAllAudit() {
		List<Audit> list = new ArrayList<>();
		auditRepository.findAll().forEach(e -> list.add(e));
		return list;
    }

    @Override
    public Audit saveAudit(Audit audit) {
        return auditRepository.save(audit);
    }
    
}
