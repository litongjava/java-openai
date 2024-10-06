package com.litongjava.jian.rerank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class RerankResult {
  private Integer index;
  private RerankDocument document;
  private double relevance_score;
}
