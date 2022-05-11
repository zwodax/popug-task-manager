package com.popuginc.tman.event;

import com.popuginc.tman.repository.UserRepository;
import com.popuginc.tman.repository.model.User;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountEventListener {

  private final UserRepository userRepository;

  @KafkaListener(topics = "accounts-stream", groupId = "task-service-listener")
  @Transactional
  public void handleAccountEvent(String event) {
    JSONObject jsonObject = new JSONObject(event);
    String eventType = jsonObject.getString("eventType");
    JSONObject data = jsonObject.getJSONObject("data");

    if (Objects.equals(eventType, "Accounts.Created")) {
      handleAccountsCreated(data);
    } else if (Objects.equals(eventType, "Accounts.RoleChanged")) {
      handleRoleChanged(data);
    }
  }

  private void handleRoleChanged(JSONObject jsonObject) {
    String userId = jsonObject.getString("userId");
    String role = jsonObject.getString("role");

    userRepository.findByPublicId(UUID.fromString(userId)).ifPresent(u ->
        u.setRole(role));
  }

  private void handleAccountsCreated(JSONObject jsonObject) {
    String userId = jsonObject.getString("id");
    String email = jsonObject.getString("email");
    String role = jsonObject.getString("role");
    String username = jsonObject.getString("username");

    User user = User.builder()
        .publicId(UUID.fromString(userId))
        .email(email)
        .role(role)
        .username(username)
        .build();
    userRepository.save(user);
  }


}
