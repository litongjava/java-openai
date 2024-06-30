package com.litongjava.openai.embedding;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingData {
  private String object;
  private Integer index;
  private List<Double> embedding;
}
