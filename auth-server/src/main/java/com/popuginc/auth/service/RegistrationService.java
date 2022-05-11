package com.popuginc.auth.service;


import com.popuginc.auth.events.AccountCreatedEvent;
import com.popuginc.auth.events.AccountCreatedEvent.AccountCreatedPayload;
import com.popuginc.auth.repository.UserRepository;
import com.popuginc.auth.repository.model.Role;
import com.popuginc.auth.repository.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final KafkaTemplate<Long, AccountCreatedEvent> accountsStream;

  @Transactional
  public void register(String username, String password, String email, Role role) {

    User user = User.builder()
        .publicId(UUID.randomUUID())
        .username(username)
        .email(email)
        .password(passwordEncoder.encode(password))
        .role(role)
        .build();

    User savedUser = userRepository.save(user);

    AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(AccountCreatedPayload
        .builder()
        .id(savedUser.getPublicId())
        .username(savedUser.getUsername())
        .email(savedUser.getEmail())
        .role(savedUser.getRole())
        .build());

    accountsStream.send("accounts-stream", savedUser.getId(), accountCreatedEvent);
  }
}
