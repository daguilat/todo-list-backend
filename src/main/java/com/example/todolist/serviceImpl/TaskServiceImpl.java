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
    public List<Task> getAllTask(int user_id) {
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
    public Task createTask(Task task, int user_id) {
        //Validating user
        Optional<User> user = userRepository.findByUsername(task.getUser().getUsername());
        if(user.isPresent()){
            task.setUser(user.get());
        }
        //If no username was send, getting random user with less tasks
        else {
            Optional<User> randomUser = userRepository.findRandomLessTasksUser();
            if(randomUser.isPresent()){
                user = randomUser;
            } else {
                //Failed to assign random user to task
            }
        }
        // Getting new task id
        int task_id = taskRepository.getTaskId();
        task.setTask_id(task_id);
        
        task = taskRepository.save(task);

        //Registering user activity: creating tasks
        Optional<User> user_creator = userRepository.findById(user_id);
        auditService.saveAudit(user_creator.get(), null, "User created new task of id: "+task_id);

        return task;
    }

    @Override
    public Task updateTask(Task task, int user_id) {
        //Getting user
        Optional<User> user = userRepository.findByUsername(task.getUser().getUsername());
        if(!user.isPresent()){
            //Selected user was not found
        }

        taskRepository.save(task);
        //Registering user activity: updating tasks
        Optional<User> user_updater = userRepository.findById(user_id);
        auditService.saveAudit(user_updater.get(), null, "User updated task of id: "+task.getTask_id());

        return task;
    }

    @Override
    public void deleteTask(int task_id, int user_id) {
        // Getting task
        Optional<Task> task = taskRepository.findById(task_id);
        if(!task.isPresent())
            //Task not found
        
        // Deleting task
        taskRepository.delete(task.get());

        //Registering user activity: deleting tasks
        Optional<User> user_deleter = userRepository.findById(user_id);
        auditService.saveAudit(user_deleter.get(), null, "User deleted task of id: "+task.get().getTask_id());
       
    }
    
}
