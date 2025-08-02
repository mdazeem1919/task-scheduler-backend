package com.example.taskscheduler.service;

import com.example.taskscheduler.dto.TaskRequestDTO;
import com.example.taskscheduler.dto.TaskResponseDTO;
import com.example.taskscheduler.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO taskDTO);
    TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO updatedDTO);
    void deleteTask(UUID taskId);
    TaskResponseDTO getTaskById(UUID taskId);
    Page<TaskResponseDTO> getTasks(TaskStatus status, Priority priority, List<String> tags, Pageable pageable);
    TaskResponseDTO addNote(UUID taskId, String noteContent);
    TaskResponseDTO simulateProcessing(UUID taskId);
    List<TaskResponseDTO> markOverdueTasksAsFailed();
}