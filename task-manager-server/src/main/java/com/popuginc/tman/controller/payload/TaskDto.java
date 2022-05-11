package com.popuginc.tman.controller.payload;

import com.popuginc.tman.repository.model.TaskStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {

  private Long id;

  private UUID publicId;

  private String name;

  private String description;

  private TaskStatus status;

}
