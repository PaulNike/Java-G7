package com.codigo.api_rest_g7.ejercicio6.controller;

import com.codigo.api_rest_g7.ejercicio6.model.TaskModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    List<TaskModel> tasks = new ArrayList<>();

    @GetMapping
    public List<TaskModel> getTasks(){
        return tasks;
    }
    @PostMapping
    public String addTask(@RequestBody TaskModel taskModel){
        tasks.add(taskModel);
        return "Task añadida: " + taskModel.toString();
    }

    @DeleteMapping("/{index}")
    public String deleteTask(@PathVariable int index){
        if(index >= 0 && index < tasks.size()){
            return "Task Removida: " + tasks.remove(index);
        }else{
            throw new TaskNotFoundException();
        }
    }
}
@ResponseStatus(HttpStatus.NOT_FOUND)
class TaskNotFoundException extends RuntimeException{

}
