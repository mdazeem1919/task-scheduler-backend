package com.example.taskscheduler.scheduler;

import com.example.taskscheduler.model.Task;
import com.example.taskscheduler.model.TaskStatus;
import com.example.taskscheduler.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

@Component
public class TaskProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TaskProcessor.class);
    private static final Random random = new Random();

    private final TaskRepository taskRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // 5 parallel processors

    public TaskProcessor(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(fixedRate = 15_000) // runs every 15 seconds
    public void processInProgressTasks() {
        List<Task> inProgressTasks = taskRepository.findByStatus(TaskStatus.IN_PROGRESS);
        logger.info("Found {} IN_PROGRESS task(s) to process...", inProgressTasks.size());

        for (Task task : inProgressTasks) {
            executorService.submit(() -> processTask(task));
        }
    }

    private void processTask(Task task) {
        UUID taskId = task.getId();
        String title = task.getTitle();

        try {
            int delay = 2 + random.nextInt(5); // 2–6 sec
            logger.info("Processing task [{}]: sleeping for {}s...", taskId, delay);
            Thread.sleep(delay * 1000L);

            boolean success = random.nextBoolean();
            task.setStatus(success ? TaskStatus.COMPLETED : TaskStatus.FAILED);
            taskRepository.save(task);

            logger.info("Task [{}] - '{}' marked as {}", taskId, title, task.getStatus());

        } catch (Exception e) {
            logger.error("Failed to process task [{}]: {}", taskId, e.getMessage());
        }
    }
}