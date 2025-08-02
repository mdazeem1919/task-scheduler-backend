# 🤖 prompts.md — Prompt Engineering Log

This document captures all prompts and responses used from AI tools (ChatGPT and Copilot) during the development of the Task Scheduler System.

---

## 1. 📦 Project Setup

**Prompt:**
> Generate a `pom.xml` for a Spring Boot project (Java 8) using H2, Spring Web, Spring Data JPA, and OpenAPI/Swagger.

**Generated:**  
Basic pom with dependencies.

**Modifications:**
- Added `java.version` as 1.8
- Manually fixed `spring-boot.version` compatibility issues for Java 8

---

## 2. 📄 Task Entity

**Prompt:**
> Create a JPA entity for a `Task` with fields like id, title, status, tags, priority, dueAt, createdAt, notes.

**Generated:**  
Full `@Entity` class with UUID, enums, and relationships.

**Modifications:**
- Removed Lombok due to IntelliJ plugin issue
- Manually generated getters/setters, equals/hashCode

---

## 3. 📄 Note Entity

**Prompt:**
> Generate a JPA entity for a `Note` that links to a Task and has createdAt and content fields.

**Generated:**  
Correct `@ManyToOne` mapping with `JoinColumn`.

**Modifications:**
- Added constructor, getter/setters manually

---

## 4. 🧠 TaskServiceImpl

**Prompt:**
> Create a `TaskServiceImpl` with methods to create, update, delete, filter tasks and add notes.

**Generated:**  
Most business logic was AI-assisted.

**Modifications:**
- Added null checks, adjusted filtering logic
- Manually handled pagination + filtering via Java Streams

---

## 5. 🧪 Unit Tests: `TaskServiceImplTest`

**Prompt:**
> Generate JUnit 5 and Mockito tests for TaskServiceImpl.

**Generated:**  
All core methods tested: createTask, updateTask, simulateProcessing, addNote.

**Modifications:**
- Fixed `Instant.now()` mismatch
- Manually added verification of mock calls

---

## 6. 🕰️ Background Scheduler

**Prompt:**
> Add a Spring `@Scheduled` task that runs every 1 minute and marks overdue tasks as FAILED.

**Generated:**  
Scheduled method with service call.

**Modifications:**
- Added logger inside method
- Renamed method to `checkAndFailOverdueTasks`

---

## 7. 🧪 Scheduler Tests

**Prompt:**
> Write unit test for `@Scheduled` task that marks overdue tasks as failed.

**Generated:**  
Mockito + JUnit test that mocks `TaskService`.

**Modifications:**
- Renamed test class
- Added multiple task mocks

---

## 8. 🌐 Swagger/OpenAPI Setup

**Prompt:**
> Add Swagger/OpenAPI support for Spring Boot app.

**Generated:**  
`GroupedOpenApi` bean + `@OpenAPIDefinition`.

**Modifications:**
- Updated import to use `org.springdoc.core.models.GroupedOpenApi`
- Manually tested Swagger UI at `/swagger-ui.html`

---

---

## 9. 📦 DTO Refactor

**Prompt:**
> Add DTOs to decouple internal entity from API response/request.

**Generated:**
- `TaskRequestDTO`, `TaskResponseDTO`, and `NoteDTO`

**Modifications:**
- Included `id`, `timestamps` in response DTOs
- Separated input from output for clarity and API safety

---

## 10. 🔁 Mapper Layer

**Prompt:**
> Write a utility class to map between DTOs and entities.

**Generated:**  
Manual static utility class `TaskMapper`.

**Modifications:**
- Handled `Note` → `NoteDTO`
- Added `fromRequestDTO()` and `toResponseDTO()` methods
- Used inside service and test layers

---

## 11. 🧠 Service + Controller Refactor (with DTOs)

**Prompt:**
> Update service and controller to use DTOs and mappers.

**Generated:**  
Used `TaskMapper` in all flow points.

**Modifications:**
- All controller endpoints accept/send DTOs only
- Controller simplified to delegate to service layer
- Added pagination handling

---

## 12. 🔄 Multithreading Support

**Prompt:**
> Simulate task processing using multi-threading with an executor service.

**Generated:**  
Executor-based logic with task queues.

**Modifications:**
- Scheduled pool submits task for "processing"
- Processing state simulated by sleep and then task status updated

---

## 13. 🧪 DTO + Mapper Tests

**Prompt:**
> Update service and scheduler tests to verify mapping and DTO usage.

**Generated:**  
Tests now use DTOs instead of raw entity access.

**Modifications:**
- Verified `TaskMapper.toResponseDTO()` output
- Used `TaskRequestDTO` for input consistency

---

## 14. 🎯 Final Submission: `README.md`, Architecture Diagrams

**Prompt:**
> Create final project README and UML/architecture diagrams.

**Generated:**
- Clean README with endpoints, features, build/run instructions
- Component diagram, scheduler sequence diagram
- Swagger docs accessible via `/swagger-ui.html`

**Modifications:**
- Saved diagrams in `/docs/` folder
- Used draw.io-compatible PNG + XML formats

---

## ✅ Summary

The entire system was scaffolded and evolved using iterative prompts in ChatGPT. The key wins were:

- Rapid scaffolding of base structure
- Safe refactor to DTO-based architecture
- Multithreading simulation & scheduler integration
- End-to-end test coverage and prompt traceability
- Fully documented Swagger UI + OpenAPI

Manual additions were primarily for:
- Java 8 & Spring compatibility
- Swagger config + imports
- Maven + application setup
- Visual diagrams