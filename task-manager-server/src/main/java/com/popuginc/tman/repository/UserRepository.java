package com.popuginc.tman.repository;

import com.popuginc.tman.repository.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByPublicId(UUID publicId);

  Optional<User> findByUsername(String username);

  List<User> findAllByRole(String role);
}
