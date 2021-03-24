package com.example.todolist.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todolist/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    //Controller to get list of all tasks
    //Input: the user that is getting the list for audit purposes
    //Output: list of tasks
    @GetMapping("/list")
    public ResponseEntity<List<Task>> getAllTask(HttpServletRequest httpServletRequest){
        return new ResponseEntity<List<Task>>(taskService.getAllTask(), HttpStatus.OK);
    }

    //Controller to sagve a task
    //Input: the user that is creating or editing the task and the task data to create
    //Output: task
    @PostMapping("/save/{user_id}")
    public ResponseEntity<Task> createTask(HttpServletRequest httpSevletRequest,
                                           @PathVariable Integer user_id, 
                                           @RequestBody Task task){
        return new ResponseEntity<Task>(taskService.saveTask(task, user_id), HttpStatus.OK);
    }
    
    //Controller to delete a task
    //Input: the user that is deleting the task and the task id to delete
    //Output: the response 
    @DeleteMapping("/delete/{task_id}/{user_id}")
    public ResponseEntity<Map<String, String>> deleteTask(HttpServletRequest httpSevletRequest,
                                             @PathVariable Integer user_id, 
                                             @PathVariable Integer task_id){
        Map<String, String> result = taskService.deleteTask(task_id, user_id);
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
}
