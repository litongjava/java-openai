package com.litongjava.bailian.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiModalTaskMetric {
  private Integer FAILED,SUCCEEDED,TOTAL;
}
