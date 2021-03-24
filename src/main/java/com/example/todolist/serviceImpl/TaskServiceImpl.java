package com.example.todolist.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<Task> getAllTask() {
        List<Task> list = new ArrayList<>();
		taskRepository.getAllTasks().forEach(e -> list.add(e));
		return list;
    }

    @Override
    public Task saveTask(Task task, Integer user_id) {
        //Validating user
        Optional<User> user = userRepository.findByUsername(task.getUser().getUsername());
        if(user.isPresent()){
            task.setUser(user.get());
        }
        //If no username was send, getting random user with less tasks
        else {
            Optional<User> randomUserWithoutTasks = userRepository.findRandomUserWithoutTasks();
            if(randomUserWithoutTasks.isPresent()){
                user = randomUserWithoutTasks;
                task.setUser(randomUserWithoutTasks.get());
            } else {
                Optional<User> randomUserWithLessAmountOfTasks = userRepository.findRandomUserWithoutLessAmountOfTasks();
                if(randomUserWithLessAmountOfTasks.isPresent()){
                    user = randomUserWithLessAmountOfTasks;
                    task.setUser(randomUserWithLessAmountOfTasks.get());
                }
            }
        }

        if(task.getTask_id() == null){
            // Getting new task id
            int task_id = taskRepository.getTaskId();
            task.setTask_id(task_id);
            
            task = taskRepository.save(task);
    
            //Registering user activity: creating tasks
            Optional<User> user_creator = userRepository.findById(user_id);
            auditService.saveAudit(user_creator.get(), null, "User created task of id: "+task_id);

        } else {
            task = taskRepository.save(task);
    
            //Registering user activity: creating tasks
            Optional<User> user_modifier = userRepository.findById(user_id);
            auditService.saveAudit(user_modifier.get(), null, "User modified task of id: "+task.getTask_id());

        }
        return task;
    }

    @Override
    public Map<String, String> deleteTask(Integer task_id, Integer user_id) {
        Map<String, String> result = new HashMap<>();  
        // Getting task
        Optional<Task> task = taskRepository.findById(task_id);
        if(!task.isPresent()){
            //Task not found
            result.put("status","400");
            result.put("message","The task you are trying to delete was not found");
            return result;
        }
        
        // Deleting task
        taskRepository.delete(task.get());

        //Registering user activity: deleting tasks
        Optional<User> user_deleter = userRepository.findById(user_id);
        auditService.saveAudit(user_deleter.get(), null, "User deleted task of id: "+task.get().getTask_id());
       
        result.put("status","200");
        result.put("message","Task deleted");
        return result;
    }
    
}
