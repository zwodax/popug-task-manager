package com.popuginc.auth.controller;

import com.popuginc.auth.controller.payload.UserDTO;
import com.popuginc.auth.repository.model.Role;
import com.popuginc.auth.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserDTO> getUsers() {
    return userService.getUsers();
  }

  @PostMapping("/{userId}/changeRole")
  @PreAuthorize("hasRole('ADMIN')")
  public void changeRole(@PathVariable Long userId,
      @RequestBody Role role) {
    userService.changeRole(userId, role);
  }

}
