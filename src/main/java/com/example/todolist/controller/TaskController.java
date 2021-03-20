package com.example.todolist.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todolist/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<Task>> getAllTask(HttpServletRequest httpServletRequest,
                                                 @PathVariable int user_id) throws Exception {
        return new ResponseEntity<List<Task>>(taskService.getAllTask(user_id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(HttpServletRequest httpSevletRequest, @RequestBody Task task) throws Exception {
        return new ResponseEntity<Task>(taskService.createTask(task), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(HttpServletRequest httpSevletRequest, @RequestBody Task task) throws Exception {
        return new ResponseEntity<Task>(taskService.updateTask(task), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{task_id}")
    public ResponseEntity<String> deleteTask(HttpServletRequest httpSevletRequest, @PathVariable int task_id) throws Exception {
        taskService.deleteTask(task_id);
        return new ResponseEntity<String>("Task deleted correctly", HttpStatus.OK);
    }
}
