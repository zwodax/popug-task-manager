package com.popuginc.tman.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic tasksStream() {
    return TopicBuilder.name("tasks-stream")
        .partitions(1)
        .replicas(1)
        .compact()
        .build();
  }

  @Bean
  public NewTopic taskLifecycle() {
    return TopicBuilder.name("task-lifecycle")
        .partitions(1)
        .replicas(1)
        .compact()
        .build();
  }
}
