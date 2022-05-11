package com.popuginc.tman.repository;

import com.popuginc.tman.repository.model.Task;
import com.popuginc.tman.repository.model.TaskStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> getAllByStatus(TaskStatus taskStatus);
}
