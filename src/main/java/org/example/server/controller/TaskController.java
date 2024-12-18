package org.example.server.controller;

import org.example.server.model.Task;
import org.example.server.service.TaskService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @MessageMapping("task/{id}")
    public Mono<Task> getTask(@DestinationVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    @MessageMapping("task/all")
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }


    @MessageMapping("task/new")
    public Mono<Void> setAlert(Mono<Task> taskMono) {
        System.out.println("creating task");
        return taskMono
                .doOnNext(task -> taskService.createTask(task.getDescription()))
                .thenEmpty(Mono.empty());
    }


    @MessageMapping("task/batchcreate")
    public Flux<Task> batchCreate(Flux<Task> tasks) {
        return tasks
                .flatMap(task -> taskService.createTask(task.getDescription()));
    }

    @MessageMapping("task/{id}")
    public Mono<Task> completeTask(@DestinationVariable("id") Long id) {
        return taskService.completeTask(id);
    }
    //Семейкин Сергей ИКБО-16-21 (SmknSe)
}