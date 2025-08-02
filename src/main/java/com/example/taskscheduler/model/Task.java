package com.example.taskscheduler.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class Task {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36, unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp dueAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    public Task() {}

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

    public List<Note> getNotes() {
        return notes == null ? null : new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = (notes == null) ? new ArrayList<>() : new ArrayList<>(notes);
    }

    public List<String> getTags() {
        return tags == null ? null : new ArrayList<>(tags);
    }

    public void setTags(List<String> tags) {
        this.tags = (tags == null) ? new ArrayList<>() : new ArrayList<>(tags);
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    // Add constructor, toString, equals and hashCode methods as needed in the current class
    public Task(UUID id, String title, String description, TaskStatus status, Timestamp createdAt, Timestamp dueAt, Priority priority, List<String> tags, List<Note> notes) {
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", dueAt=" + dueAt +
                ", priority=" + priority +
                ", tags=" + tags +
                ", notesCount=" + (notes == null ? 0 : notes.size()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}