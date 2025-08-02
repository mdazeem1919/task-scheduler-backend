package com.example.taskscheduler.repository;

import com.example.taskscheduler.model.Task;
import com.example.taskscheduler.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStatusAndDueAtBefore(TaskStatus status, Timestamp now);
    List<Task> findByStatus(TaskStatus status);
}