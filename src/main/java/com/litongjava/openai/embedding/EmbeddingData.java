package com.litongjava.openai.embedding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmbeddingData {
  private String object;
  private Integer index;
  private Float[] embedding;
}
