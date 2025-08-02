package com.example.taskscheduler.scheduler;

import com.example.taskscheduler.dto.TaskResponseDTO;
import com.example.taskscheduler.service.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskOverdueScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskOverdueScheduler.class);

    private final TaskService taskService;

    public TaskOverdueScheduler(TaskService taskService) {
        this.taskService = taskService;
    }

    // Runs every 1 minute
    @Scheduled(fixedRate = 60_000)
    public void checkAndFailOverdueTasks() {
        List<TaskResponseDTO> failedTasks = taskService.markOverdueTasksAsFailed();

        if (!failedTasks.isEmpty()) {
            logger.info("Overdue tasks marked as FAILED:");
            for (TaskResponseDTO task : failedTasks) {
                logger.info("Task ID: {}, Title: '{}', Due: {}, Status: {}",
                        task.getId(),
                        task.getTitle(),
                        task.getDueAt(),
                        task.getStatus());
            }
        } else {
            logger.debug("No overdue tasks to mark as failed.");
        }
    }
}