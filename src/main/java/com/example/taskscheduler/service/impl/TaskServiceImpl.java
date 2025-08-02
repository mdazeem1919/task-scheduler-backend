package com.example.taskscheduler.service.impl;

import com.example.taskscheduler.dto.*;
import com.example.taskscheduler.mapper.TaskMapper;
import com.example.taskscheduler.model.*;
import com.example.taskscheduler.repository.*;
import com.example.taskscheduler.service.TaskService;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final NoteRepository noteRepository;

    public TaskServiceImpl(TaskRepository taskRepository, NoteRepository noteRepository) {
        this.taskRepository = taskRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskDTO) {
        Task task = TaskMapper.fromRequestDTO(taskDTO);
        task.setId(null); // Auto-generate
        task.setStatus(TaskStatus.OPEN);
        task.setCreatedAt(Timestamp.from(Instant.now()));
        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO updatedDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(updatedDTO.getTitle());
        task.setDescription(updatedDTO.getDescription());
        task.setDueAt(updatedDTO.getDueAt());
        task.setPriority(updatedDTO.getPriority());
        task.setTags(updatedDTO.getTags());

        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(UUID taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskResponseDTO getTaskById(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return TaskMapper.toResponseDTO(task);
    }

    @Override
    public Page<TaskResponseDTO> getTasks(TaskStatus status, Priority priority, List<String> tags, Pageable pageable) {
        List<Task> all = taskRepository.findAll();
        List<Task> filtered = all.stream()
                .filter(t -> status == null || t.getStatus() == status)
                .filter(t -> priority == null || t.getPriority() == priority)
                .filter(t -> tags == null || tags.isEmpty() || t.getTags().containsAll(tags))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<TaskResponseDTO> responseList = filtered.subList(start, end).stream()
                .map(TaskMapper::toResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, filtered.size());
    }

    @Override
    public TaskResponseDTO addNote(UUID taskId, String noteContent) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Note note = new Note();
        note.setContent(noteContent);
        note.setCreatedAt(Timestamp.from(Instant.now()));
        note.setTask(task);

        noteRepository.save(note);
        task.addNote(note);

        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO simulateProcessing(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getStatus() != TaskStatus.OPEN) {
            throw new RuntimeException("Task is not in OPEN state");
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        return TaskMapper.toResponseDTO(taskRepository.save(task));
    }

    @Override
    public List<TaskResponseDTO> markOverdueTasksAsFailed() {
        List<Task> overdue = taskRepository.findByStatusAndDueAtBefore(TaskStatus.OPEN, Timestamp.from(Instant.now()));
        for (Task task : overdue) {
            task.setStatus(TaskStatus.FAILED);
        }
        return taskRepository.saveAll(overdue).stream()
                .map(TaskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}