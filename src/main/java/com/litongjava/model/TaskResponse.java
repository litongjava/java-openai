package com.litongjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
  private Long id;
  private String result;

  public TaskResponse(String markdown) {
    this.result = markdown;
  }

  public TaskResponse(Long taskId) {
    this.id = taskId;
  }

}
