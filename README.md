# ✅ Task Scheduler System

A Spring Boot backend system to manage and simulate the lifecycle of internal tasks (e.g., IT Helpdesk tickets or Operations requests).  
Supports full task CRUD, overdue tracking via background scheduler, multi-threaded processing, and OpenAPI Swagger UI.

---

## 🚀 Features

- ✅ Create, update, delete, and fetch tasks via REST API
- 🕰️ Automatically marks overdue tasks as **FAILED** every minute
- 🧵 Simulates task processing in parallel threads
- 🧾 Add notes to any task
- 📊 Filtering and pagination on tasks
- 🔁 DTO mapping to decouple API from persistence
- 🌐 Swagger/OpenAPI UI at `/swagger-ui.html`

---

## 🧰 Tech Stack

| Layer       | Technology                      |
|-------------|---------------------------------|
| Language    | Java 8                          |
| Framework   | Spring Boot 2.7.8               |
| Database    | H2 (In-Memory)                  |
| ORM         | Spring Data JPA                 |
| API Docs    | Springdoc OpenAPI 1.7.0         |
| Testing     | JUnit 5, Mockito                |
| Build Tool  | Maven                           |
| Scheduler   | `@Scheduled` tasks              |
| Concurrency | `ExecutorService` (thread pool) |

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/example/taskscheduler/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── scheduler/
│   │   ├── service/
│   │   └── TaskSchedulerApplication.java
│   └── resources/
│       ├── application.properties
│       └── schema.sql (optional)
├── test/
│   └── java/com/example/taskscheduler/
│       ├── service/TaskServiceImplTest.java
│       └── scheduler/TaskOverdueSchedulerTest.java
```

---

## ⚙️ Running the Project

### 🛠️ Build the App

```bash
   mvn clean install
```

### ▶️ Run the App

```bash
   mvn spring-boot:run
```

By default, the app runs at:  
📍 `http://localhost:8080`

---

### 🧪 Swagger/OpenAPI Docs

- Swagger UI: 👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: 📄 [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🔁 Core REST APIs

### 📌 Tasks

| Method | Endpoint             | Description                  |
|--------|----------------------|------------------------------|
| GET    | `/api/tasks`         | List tasks (supports filters)|
| GET    | `/api/tasks/{id}`    | Get task by ID               |
| POST   | `/api/tasks`         | Create new task              |
| PUT    | `/api/tasks/{id}`    | Update task                  |
| DELETE | `/api/tasks/{id}`    | Delete task                  |

### ✏️ Notes

| Method | Endpoint                     | Description        |
|--------|------------------------------|--------------------|
| POST   | `/api/tasks/{id}/notes`      | Add note to task   |

### ⚙️ Processing Simulation

| Method | Endpoint                     | Description              |
|--------|------------------------------|--------------------------|
| POST   | `/api/tasks/{id}/process`    | Simulate processing a task |

---

## 🧠 Scheduler

Runs every **60 seconds** to:

- Fetch tasks with:
    - `status = OPEN`
    - `dueAt < now`
- Update them to `FAILED`
- Log the list of updated tasks

---

## 🧵 Multi-threaded Processing

- Uses `ExecutorService` to simulate parallel task execution
- Transitions task from `OPEN → IN_PROGRESS → COMPLETED`
- Simulates delay via `Thread.sleep()` during mock processing

---

## 🧪 Testing

- `TaskServiceImplTest`: Full JUnit + Mockito coverage for service layer
- `TaskOverdueSchedulerTest`: Verifies scheduler calls service correctly

Run all tests:

```bash
   mvn test
```

---

## 📄 Documentation Files

| File                    | Description                         |
|-------------------------|-------------------------------------|
| `README.md`             | This project documentation          |
| `prompts.md`            | All ChatGPT prompts used     |
| `application.properties`| DB and Swagger settings             |
| `architecture.pptx`     | High-level design diagrams          |
| `docs/*.drawio`         | Architecture diagrams               |

---

## 📝 Submission Notes

- All features tested and documented
- Swagger UI confirms working API
- Tasks correctly processed and marked
- DTOs + mappers decouple persistence and API
