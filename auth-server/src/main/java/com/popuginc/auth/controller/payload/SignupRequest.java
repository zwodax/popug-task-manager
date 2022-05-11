package com.popuginc.auth.controller.payload;

import com.popuginc.auth.repository.model.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Role role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

}
