package com.example.todolist.controller;

import java.util.List;

import com.example.todolist.model.Audit;
import com.example.todolist.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todolist/audit")
public class AuditController {

    @Autowired
    AuditService auditService;

    //Controller to get all audit information
    //Output: List of audit
    @GetMapping("/list")
    public ResponseEntity<List<Audit>> getAllAudit(){
        return new ResponseEntity<List<Audit>>(auditService.getAllAudit(), HttpStatus.OK);
    }
    
}
