package com.example.todolist.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.AuditService;
import com.example.todolist.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditService auditService;

    @Override
    public List<Task> getAllTask(int user_id) throws Exception {
        Optional<User> user = userRepository.findById(user_id);
        if(user.isPresent()){
            //Registering user activity: finding all tasks
            auditService.saveAudit(user.get(), null, "User getting all tasks");
        }
        List<Task> list = new ArrayList<>();
		taskRepository.findAll().forEach(e -> list.add(e));
		return list;
    }

    @Override
    public Task createTask(Task task) throws Exception {
        //Getting user
        Optional<User> user = userRepository.findByUsername(task.getUser().getUsername());
        if(user.isPresent()){
            task.setUser(user.get());
        }
        //If no username was send, getting random user with less tasks
        else {
            Optional<User> randomUser = userRepository.findRandomLessTasksUser();
            if(randomUser.isPresent()){
                user = randomUser;
            }
        }
        // Getting new task id
        int task_id = taskRepository.getTaskId();
        task.setTask_id(task_id);
        
        task = taskRepository.save(task);

        //Registering user activity: creating tasks
        auditService.saveAudit(user.get(), null, "User created new task of id: "+task_id);

        return task;
    }

    @Override
    public Task updateTask(Task task) throws Exception {
        //Getting user
        Optional<User> user = userRepository.findByUsername(task.getUser().getUsername());
        if(user.isPresent()){
            taskRepository.save(task);
            //Registering user activity: updating tasks
            auditService.saveAudit(user.get(), null, "User updated task of id: "+task.getTask_id());

            return task;
        } else 
            throw new Exception("No user was found.");
    }

    @Override
    public void deleteTask(int task_id) throws Exception {
        // Getting task
        Optional<Task> task = taskRepository.findById(task_id);
        if(!task.isPresent())
            throw new Exception("Task not found. Task ID: " + task_id);
        
        // Deleting task
        taskRepository.delete(task.get());

        //Registering user activity: deleting tasks
       


    }
    
}
