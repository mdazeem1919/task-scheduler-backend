package com.example.taskscheduler.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class NoteDTO {
    private UUID id;
    private String content;
    private Timestamp createdAt;

    public NoteDTO() {
    }

    public NoteDTO(UUID id, String content, Timestamp createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}