package com.example.todolist.repository;

import java.util.List;

import com.example.todolist.model.Audit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<Audit, Integer>{
    
    //Query to get the next audit_id in the sequence
    //Output: The next audit id
    @Query(value = "SELECT NEXTVAL('audit_id_seq')", nativeQuery = true)
    Integer getAuditId(); 

    //Query to get the list of audits by audit_id descending order
    //Output: the audit list
    @Query(value = "SELECT * FROM audit ORDER BY audit_id DESC", nativeQuery = true)
    List<Audit> getAllAudit();
}
