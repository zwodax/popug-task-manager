package com.popuginc.auth.controller.payload;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class JwtResponse {

  private final String token;
  private final String type = "Bearer";
  private final UUID id;
  private final String username;
  private final String email;
  private final List<String> roles;

}
