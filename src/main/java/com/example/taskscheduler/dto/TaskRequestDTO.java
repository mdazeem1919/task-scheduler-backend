package com.example.taskscheduler.dto;

import com.example.taskscheduler.model.Priority;
import java.sql.Timestamp;
import java.util.List;

public class TaskRequestDTO {
    private String title;
    private String description;
    private Timestamp dueAt;
    private Priority priority;
    private List<String> tags;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, Timestamp dueAt, Priority priority, List<String> tags) {
        this.title = title;
        this.description = description;
        this.dueAt = dueAt;
        this.priority = priority;
        this.tags = tags;
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
}