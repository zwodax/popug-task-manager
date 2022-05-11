package com.popuginc.tman.event.model.task;

import com.popuginc.tman.event.model.GenericEvent;
import com.popuginc.tman.repository.model.TaskStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;


public class TaskCreatedEvent extends GenericEvent<TaskCreatedEvent.Payload> {

  public TaskCreatedEvent(Payload data) {
    super("Tasks.Created", data);
  }

  @Data
  @Builder
  public static class Payload {

    private UUID id;

    private String name;

    private String description;

    private TaskStatus status;
  }

}
