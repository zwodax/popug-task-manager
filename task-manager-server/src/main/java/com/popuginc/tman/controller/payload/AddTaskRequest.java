package com.popuginc.tman.controller.payload;

import java.util.UUID;
import lombok.Data;

@Data
public class AddTaskRequest {

  private String name;

  private String description;

  private UUID userId;

}
