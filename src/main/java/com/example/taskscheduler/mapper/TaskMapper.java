package com.example.taskscheduler.mapper;

import com.example.taskscheduler.dto.*;
import com.example.taskscheduler.model.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskMapper {

    // Task → TaskResponseDTO
    public static TaskResponseDTO toResponseDTO(Task task) {
        List<NoteDTO> noteDTOs = task.getNotes().stream()
                .map(TaskMapper::toNoteDTO)
                .collect(Collectors.toList());

        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getDueAt(),
                task.getPriority(),
                task.getTags(),
                noteDTOs
        );
    }

    // TaskRequestDTO → Task (for creation)
    public static Task fromRequestDTO(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueAt(dto.getDueAt());
        task.setPriority(dto.getPriority());
        task.setTags(dto.getTags());
        return task;
    }

    // Note → NoteDTO
    public static NoteDTO toNoteDTO(Note note) {
        return new NoteDTO(
                note.getId(),
                note.getContent(),
                note.getCreatedAt()
        );
    }
}