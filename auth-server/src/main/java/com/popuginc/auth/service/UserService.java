package com.popuginc.auth.service;

import com.popuginc.auth.controller.payload.UserDTO;
import com.popuginc.auth.events.AccountRoleChanged;
import com.popuginc.auth.events.AccountRoleChanged.RoleChangedPayload;
import com.popuginc.auth.exception.UserNotFoundException;
import com.popuginc.auth.repository.UserRepository;
import com.popuginc.auth.repository.model.Role;
import com.popuginc.auth.repository.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final KafkaTemplate<Long, AccountRoleChanged> accountsStream;

  @Transactional(readOnly = true)
  public List<UserDTO> getUsers() {
    return userRepository.findAll().stream()
        .map(user -> UserDTO.builder()
            .id(user.getPublicId())
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .build())
        .collect(Collectors.toList());
  }

  @Transactional
  public void changeRole(Long userId, Role role) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    user.setRole(role);

    AccountRoleChanged accountRoleChanged = new AccountRoleChanged(
        RoleChangedPayload.builder().publicId(user.getPublicId())
            .role(role).build());

    accountsStream.send("accounts-stream", userId, accountRoleChanged);
  }
}
