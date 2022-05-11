package com.popuginc.auth.controller.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

  private String username;

  private String password;

}
