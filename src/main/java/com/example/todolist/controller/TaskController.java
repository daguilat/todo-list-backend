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

    //Controller to get list of all tasks
    //Input: the user that is getting the list for audit purposes
    //Output: list of tasks
    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<Task>> getAllTask(HttpServletRequest httpServletRequest,
                                                 @PathVariable int user_id){
        return new ResponseEntity<List<Task>>(taskService.getAllTask(user_id), HttpStatus.OK);
    }

    //Controller to create a task
    //Input: the user that is creating the task and the task data to create
    //Output: created task
    @PostMapping("/create/{user_id}")
    public ResponseEntity<Task> createTask(HttpServletRequest httpSevletRequest,
                                           @PathVariable int user_id, 
                                           @RequestBody Task task){
        return new ResponseEntity<Task>(taskService.createTask(task, user_id), HttpStatus.OK);
    }

    //Controller to update a task
    //Input: the user that is updating the task and the task data to update
    //Output: updated task
    @PutMapping("/update/{user_id}")
    public ResponseEntity<Task> updateTask(HttpServletRequest httpSevletRequest,
                                           @PathVariable int user_id,
                                           @RequestBody Task task){
        return new ResponseEntity<Task>(taskService.updateTask(task, user_id), HttpStatus.OK);
    }
    
    //Controller to delete a task
    //Input: the user that is deleting the task and the task id to delete
    //Output: the response 
    @DeleteMapping("/delete/{task_id}/{user_id}")
    public ResponseEntity<String> deleteTask(HttpServletRequest httpSevletRequest,
                                             @PathVariable int user_id, 
                                             @PathVariable int task_id){
        taskService.deleteTask(task_id, user_id);
        return new ResponseEntity<String>("Task deleted correctly", HttpStatus.OK);
    }
}
