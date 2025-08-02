package com.example.taskscheduler.dto;

import com.example.taskscheduler.model.Priority;
import com.example.taskscheduler.model.TaskStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class TaskResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private Timestamp createdAt;
    private Timestamp dueAt;
    private Priority priority;
    private List<String> tags;
    private List<NoteDTO> notes;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(UUID id, String title, String description, TaskStatus status,
                           Timestamp createdAt, Timestamp dueAt, Priority priority,
                           List<String> tags, List<NoteDTO> notes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.dueAt = dueAt;
        this.priority = priority;
        this.tags = tags;
        this.notes = notes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDueAt() {
        return dueAt;
    }

    public void setDueAt(Timestamp dueAt) {
        this.dueAt = dueAt;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }
}