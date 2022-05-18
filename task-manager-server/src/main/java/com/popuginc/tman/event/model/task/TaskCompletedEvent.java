package com.popuginc.tman.event.model.task;

import com.popuginc.tman.event.model.GenericEvent;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

public class TaskCompletedEvent extends GenericEvent<TaskCompletedEvent.Payload> {

  public TaskCompletedEvent(Payload data) {
    super("Task.Completed", data);
  }

  @Data
  @Builder
  public static class Payload {

    private UUID taskId;
  }

}
