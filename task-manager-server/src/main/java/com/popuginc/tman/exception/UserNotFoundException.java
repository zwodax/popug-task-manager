package com.popuginc.tman.exception;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {

  private UUID publicId;
}
