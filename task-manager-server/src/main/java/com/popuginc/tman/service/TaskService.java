package com.popuginc.tman.service;

import com.popuginc.tman.controller.payload.TaskDto;
import com.popuginc.tman.event.model.task.TaskAssignedEvent;
import com.popuginc.tman.event.model.task.TaskCompletedEvent;
import com.popuginc.tman.event.model.task.TaskCreatedEvent;
import com.popuginc.tman.exception.TaskNotFoundException;
import com.popuginc.tman.exception.UserNotFoundException;
import com.popuginc.tman.repository.TaskRepository;
import com.popuginc.tman.repository.UserRepository;
import com.popuginc.tman.repository.model.Task;
import com.popuginc.tman.repository.model.TaskStatus;
import com.popuginc.tman.repository.model.User;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

  private static final String TASKS_STREAM = "tasks-stream";

  private final TaskRepository taskRepository;

  private final UserRepository userRepository;

  private final KafkaTemplate<UUID, TaskCreatedEvent> taskCreatedTemplate;
  private final KafkaTemplate<UUID, TaskCompletedEvent> taskCompletedTemplate;
  private final KafkaTemplate<UUID, TaskAssignedEvent> taskAssignedTemplate;


  private final Random rand = new Random();

  @Transactional
  public void addTask(String name, String description, UUID userPublicId) {
    User user = userRepository.findByPublicId(userPublicId).orElseThrow(() ->
        new UserNotFoundException(userPublicId));

    if (!Objects.equals(user.getRole(), "ROLE_USER")) {
      throw new IllegalStateException("Can't assign not developer");
    }

    Task task = Task.builder()
        .publicId(UUID.randomUUID())
        .name(name)
        .description(description)
        .status(TaskStatus.IN_PROGRESS)
        .assignedUser(user)
        .build();

    Task savedTask = taskRepository.save(task);

    TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent(TaskCreatedEvent.Payload.builder()
        .id(savedTask.getPublicId())
        .name(savedTask.getName())
        .description(savedTask.getDescription())
        .status(savedTask.getStatus())
        .build());

    TaskAssignedEvent.Payload taskAssignedPayload = TaskAssignedEvent.Payload.builder()
        .taskId(savedTask.getPublicId())
        .assignedTo(userPublicId)
        .build();

    TaskAssignedEvent taskAssignedEvent = new TaskAssignedEvent(taskAssignedPayload);

    taskCreatedTemplate.send("tasks-stream", savedTask.getPublicId(), taskCreatedEvent);
    taskAssignedTemplate.send("tasks-stream", savedTask.getPublicId(), taskAssignedEvent);
  }

  @Transactional
  public void shuffleTasks() {
    List<User> developers = userRepository.findAllByRole("ROLE_USER");
    if (developers.isEmpty()) {
      return;
    }

    List<Task> tasksInProgress = taskRepository.getAllByStatus(TaskStatus.IN_PROGRESS);
    if (tasksInProgress.isEmpty()) {
      return;
    }

    for (Task task : tasksInProgress) {
      User randomDeveloper = getRandomDeveloper(developers);
      task.setAssignedUser(randomDeveloper);

      TaskAssignedEvent.Payload taskAssignedPayload = TaskAssignedEvent.Payload.builder()
          .taskId(task.getPublicId())
          .assignedTo(randomDeveloper.getPublicId())
          .build();

      TaskAssignedEvent taskAssignedEvent = new TaskAssignedEvent(taskAssignedPayload);
      taskAssignedTemplate.send(TASKS_STREAM, taskAssignedEvent.getData().getTaskId(),
          taskAssignedEvent);
    }
  }

  @Transactional
  public void completeTask(String currentUsername, Long taskId) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException(taskId));

    if (!task.getAssignedUser().getUsername().equals(currentUsername)) {
      throw new AccessDeniedException("Wrong user id");
    }

    task.setStatus(TaskStatus.COMPLETED);

    TaskCompletedEvent.Payload payload = TaskCompletedEvent.Payload.builder()
        .taskId(task.getPublicId())
        .build();

    TaskCompletedEvent taskCompletedEvent = new TaskCompletedEvent(payload);
    taskCompletedTemplate.send("tasks-stream",
        taskCompletedEvent.getData().getTaskId(), taskCompletedEvent);
  }

  private User getRandomDeveloper(List<User> developers) {
    return developers.get(rand.nextInt(developers.size()));
  }


  public List<TaskDto> getUserTasks(String name) {
    User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);

    return user.getTasks().stream().map(t -> TaskDto.builder()
            .id(t.getId())
            .publicId(t.getPublicId())
            .name(t.getName())
            .description(t.getDescription())
            .status(t.getStatus())
            .build())
        .collect(Collectors.toList());
  }
}
