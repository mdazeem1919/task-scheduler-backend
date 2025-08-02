package com.example.taskscheduler.controller;

import com.example.taskscheduler.dto.*;
import com.example.taskscheduler.model.*;
import com.example.taskscheduler.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task API", description = "Manage tasks and their notes")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Create a new task")
    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @Operation(summary = "Update an existing task")
    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable UUID id, @RequestBody TaskRequestDTO taskDTO) {
        return taskService.updateTask(id, taskDTO);
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Delete a task by ID")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }

    @Operation(summary = "Get all tasks with optional filters")
    @GetMapping
    public Page<TaskResponseDTO> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return taskService.getTasks(status, priority, tags, pageable);
    }

    @Operation(summary = "Add a note to a task")
    @PostMapping("/{id}/notes")
    public TaskResponseDTO addNote(@PathVariable UUID id, @RequestBody String content) {
        return taskService.addNote(id, content);
    }

    @Operation(summary = "Simulate processing a task")
    @PostMapping("/{id}/process")
    public TaskResponseDTO simulateProcessing(@PathVariable UUID id) {
        return taskService.simulateProcessing(id);
    }
}