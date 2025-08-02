package com.example.taskscheduler.service;

import com.example.taskscheduler.dto.TaskRequestDTO;
import com.example.taskscheduler.dto.TaskResponseDTO;
import com.example.taskscheduler.mapper.TaskMapper;
import com.example.taskscheduler.model.*;
import com.example.taskscheduler.repository.*;
import com.example.taskscheduler.service.impl.TaskServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock private TaskRepository taskRepository;
    @Mock private NoteRepository noteRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task sampleTask;
    private TaskRequestDTO sampleRequestDTO;

    @BeforeEach
    void setUp() {
        sampleRequestDTO = new TaskRequestDTO(
                "Sample Task",
                "This is a sample",
                Timestamp.from(Instant.now().plusSeconds(3600)),
                Priority.MEDIUM,
                Arrays.asList("tag1", "tag2")
        );

        sampleTask = TaskMapper.fromRequestDTO(sampleRequestDTO);
        sampleTask.setId(UUID.randomUUID());
        sampleTask.setStatus(TaskStatus.OPEN);
        sampleTask.setCreatedAt(Timestamp.from(Instant.now()));
    }

    @Test
    void testCreateTask_setsDefaultsAndSaves() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskResponseDTO created = taskService.createTask(sampleRequestDTO);

        assertThat(created).isNotNull();
        assertThat(created.getStatus()).isEqualTo(TaskStatus.OPEN);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testUpdateTask_success() {
        UUID taskId = sampleTask.getId();
        TaskRequestDTO updated = new TaskRequestDTO();
        updated.setTitle("Updated");
        updated.setDescription("Updated desc");
        updated.setDueAt(sampleTask.getDueAt());
        updated.setPriority(Priority.HIGH);
        updated.setTags(Collections.singletonList("urgent"));

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskResponseDTO result = taskService.updateTask(taskId, updated);
        assertThat(result.getTitle()).isEqualTo("Updated");
        assertThat(result.getPriority()).isEqualTo(Priority.HIGH);
    }

    @Test
    void testGetTaskById_found() {
        UUID id = sampleTask.getId();
        when(taskRepository.findById(id)).thenReturn(Optional.of(sampleTask));

        TaskResponseDTO found = taskService.getTaskById(id);
        assertThat(found.getId()).isEqualTo(sampleTask.getId());
        assertThat(found.getTitle()).isEqualTo(sampleTask.getTitle());
        assertThat(found.getDescription()).isEqualTo(sampleTask.getDescription());
        assertThat(found.getStatus()).isEqualTo(sampleTask.getStatus());
        assertThat(found.getDueAt()).isEqualTo(sampleTask.getDueAt());
        assertThat(found.getCreatedAt()).isEqualTo(sampleTask.getCreatedAt());
        assertThat(found.getPriority()).isEqualTo(sampleTask.getPriority());
        assertThat(found.getTags()).containsExactlyElementsOf(sampleTask.getTags());
        assertThat(found.getNotes()).isEmpty();
    }

    @Test
    void testGetTaskById_notFound() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(id));
    }

    @Test
    void testSimulateProcessing_setsInProgress() {
        sampleTask.setStatus(TaskStatus.OPEN);
        when(taskRepository.findById(sampleTask.getId())).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskResponseDTO result = taskService.simulateProcessing(sampleTask.getId());

        assertThat(result.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    void testSimulateProcessing_invalidState() {
        sampleTask.setStatus(TaskStatus.COMPLETED);
        when(taskRepository.findById(sampleTask.getId())).thenReturn(Optional.of(sampleTask));

        assertThrows(RuntimeException.class, () -> taskService.simulateProcessing(sampleTask.getId()));
    }

    @Test
    void testMarkOverdueTasksAsFailed() {
        List<Task> overdue = Collections.singletonList(sampleTask);
        when(taskRepository.findByStatusAndDueAtBefore(eq(TaskStatus.OPEN), any())).thenReturn(overdue);
        when(taskRepository.saveAll(anyList())).thenReturn(overdue);

        List<TaskResponseDTO> updated = taskService.markOverdueTasksAsFailed();

        assertThat(updated).hasSize(1);
        assertThat(updated.get(0).getStatus()).isEqualTo(TaskStatus.FAILED);
    }

    @Test
    void testAddNote() {
        UUID id = sampleTask.getId();
        when(taskRepository.findById(id)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskResponseDTO updated = taskService.addNote(id, "This is a note");
        assertThat(updated.getNotes()).hasSize(1);
        assertThat(updated.getNotes().get(0).getContent()).isEqualTo("This is a note");
    }
}