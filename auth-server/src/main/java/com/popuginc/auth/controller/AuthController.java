package com.popuginc.auth.controller;

import com.popuginc.auth.controller.payload.JwtResponse;
import com.popuginc.auth.controller.payload.LoginRequest;
import com.popuginc.auth.controller.payload.SignupRequest;
import com.popuginc.auth.service.AuthenticationService;
import com.popuginc.auth.service.RegistrationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final RegistrationService signupService;
  private final AuthenticationService signInService;


  @PostMapping("/login")
  public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return signInService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
  }


  @PostMapping("/signup")
  public void registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    signupService.register(signUpRequest.getUsername(), signUpRequest.getPassword(),
        signUpRequest.getEmail(),
        signUpRequest.getRole());
  }
}
