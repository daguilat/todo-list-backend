package com.example.todolist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "audit")
public class Audit {
    
    @Id
    @Column(name = "audit_id", nullable = false)
    private Integer audit_id;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name="task_id")
    private Task task;

    @Column(name = "activity")
    private String activity;

    @Column(name = "date")
    private Date date;

    //BUILDERS 
    public Audit() {}

    public Audit(Integer audit_id, User user, Task task, String activity, Date date){
        this.audit_id = audit_id;
        this.user = user;
        this.task = task;
        this.activity = activity;
        this.date = date;
    }

    //GETTER AND SETTER
    public Integer getAudit_id() {
        return audit_id;
    }

    public void setAudit_id(Integer audit_id) {
        this.audit_id = audit_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
