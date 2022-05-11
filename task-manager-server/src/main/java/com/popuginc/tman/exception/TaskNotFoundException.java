package com.popuginc.tman.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TaskNotFoundException extends RuntimeException {

  private final Long taskId;
}
