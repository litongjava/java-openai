package com.litongjava.openai.embedding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmbeddingRequestVo {
  private String input, model;

  public EmbeddingRequestVo input(String input) {
    this.input = input;
    return this;
  }

  public EmbeddingRequestVo model(String model) {
    this.model = model;
    return this;
  }
}
