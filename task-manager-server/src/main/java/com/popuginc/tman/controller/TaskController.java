package com.popuginc.tman.controller;

import com.popuginc.tman.controller.payload.AddTaskRequest;
import com.popuginc.tman.controller.payload.TaskDto;
import com.popuginc.tman.repository.model.Task;
import com.popuginc.tman.service.TaskService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tmanager")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/tasks")
  public List<TaskDto> getTasks(Principal principal) {
    return taskService.getUserTasks(principal.getName());
  }


  @PostMapping("/tasks")
  public void addTask(@RequestBody AddTaskRequest addTaskRequest) {
    taskService.addTask(addTaskRequest.getName(), addTaskRequest.getDescription(),
        addTaskRequest.getUserId());
  }

  @PostMapping("/tasks/shuffle")
  @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
  public void shuffle() {
    taskService.shuffleTasks();
  }

  @PostMapping("/tasks/{taskId}/completeTask")
  public void completeTask(Principal principal, @PathVariable Long taskId) {
    taskService.completeTask(principal.getName(), taskId);
  }
}
