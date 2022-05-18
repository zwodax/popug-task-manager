package com.popuginc.tman.repository.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID publicId;

  @NotBlank
  @Size(max = 20)
  private String name;

  @NotBlank
  @Size(max = 120)
  private String description;

  @Enumerated(value = EnumType.STRING)
  @NotNull
  private TaskStatus status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User assignedUser;
}
