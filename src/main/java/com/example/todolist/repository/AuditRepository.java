package com.example.todolist.repository;

import com.example.todolist.model.Audit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<Audit, Integer>{
    
    @Query(value = "SELECT NEXTVAL('audit_id_seq')", nativeQuery = true)
    int getAuditId(); 
}
