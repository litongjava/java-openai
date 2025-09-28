package com.litongjava.bailian.image;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiModalOutput {
  private List<MultiModalChoice> choices;
  private MultiModalTaskMetric task_metric;
}
