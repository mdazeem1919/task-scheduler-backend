package com.example.taskscheduler.scheduler;

import com.example.taskscheduler.dto.TaskResponseDTO;
import com.example.taskscheduler.model.*;
import com.example.taskscheduler.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.mockito.Mockito.*;

public class TaskOverdueSchedulerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskOverdueScheduler scheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkAndFailOverdueTasks_shouldLogAndMarkTasks() {
        TaskResponseDTO task1 = new TaskResponseDTO();
        task1.setId(UUID.randomUUID());
        task1.setTitle("Overdue Task 1");
        task1.setDueAt(Timestamp.from(Instant.now().minusSeconds(3600)));
        task1.setStatus(TaskStatus.FAILED);

        TaskResponseDTO task2 = new TaskResponseDTO();
        task2.setId(UUID.randomUUID());
        task2.setTitle("Overdue Task 2");
        task2.setDueAt(Timestamp.from(Instant.now().minusSeconds(7200)));
        task2.setStatus(TaskStatus.FAILED);

        List<TaskResponseDTO> overdueTasks = Arrays.asList(task1, task2);

        when(taskService.markOverdueTasksAsFailed()).thenReturn(overdueTasks);

        scheduler.checkAndFailOverdueTasks();

        verify(taskService, times(1)).markOverdueTasksAsFailed();
        // Logger is not tested by default, but we trust the logic path
    }

    @Test
    void checkAndFailOverdueTasks_shouldLogEmptyIfNoTasks() {
        when(taskService.markOverdueTasksAsFailed()).thenReturn(Collections.emptyList());

        scheduler.checkAndFailOverdueTasks();

        verify(taskService, times(1)).markOverdueTasksAsFailed();
        // Optional: assert no log spam when list is empty
    }
}