package com.popuginc.auth.events;

import com.popuginc.auth.events.AccountRoleChanged.RoleChangedPayload;
import com.popuginc.auth.repository.model.Role;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class AccountRoleChanged extends GenericEvent<RoleChangedPayload> {

  public AccountRoleChanged(RoleChangedPayload data) {
    super("Accounts.RoleChanged", data);
  }

  @Data
  @AllArgsConstructor
  @Builder
  public static final class RoleChangedPayload {

    private final UUID publicId;
    private final Role role;
  }
}
