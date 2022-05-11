package com.popuginc.auth.events;

import com.popuginc.auth.events.AccountCreatedEvent.AccountCreatedPayload;
import com.popuginc.auth.repository.model.Role;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AccountCreatedEvent extends GenericEvent<AccountCreatedPayload> {

  public AccountCreatedEvent(AccountCreatedPayload data) {
    super("Accounts.Created", data);
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static final class AccountCreatedPayload {

    private UUID id;

    private String username;

    private String email;

    private Role role;
  }
}
