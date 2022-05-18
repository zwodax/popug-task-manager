package com.popuginc.tman.event.model.task;

import com.popuginc.tman.event.model.GenericEvent;
import com.popuginc.tman.event.model.task.TaskAssignedEvent.Payload;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

public class TaskAssignedEvent extends GenericEvent<Payload> {

  public TaskAssignedEvent(Payload data) {
    super("Tasks.Assigned", data);
  }

  @Data
  @Builder
  public static class Payload {

    private UUID publicTaskId;
    private UUID publicAssignerId;
  }
}
