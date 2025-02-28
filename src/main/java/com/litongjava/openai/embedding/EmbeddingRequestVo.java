package com.litongjava.openai.embedding;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmbeddingRequestVo {
  private String model;
  private Object input;

  public EmbeddingRequestVo input(String input) {
    this.input = input;
    return this;
  }

  public EmbeddingRequestVo model(String model) {
    this.model = model;
    return this;
  }

  public EmbeddingRequestVo(String input) {
    this.input = input;
  }

  public EmbeddingRequestVo(List<String> input) {
    this.input = input;
  }

  public String getInputStr() {
    if (input != null) {
      return (String) input;
    }
    return null;
  }
}
