package com.litongjava.bailian.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiModalChoice {
  private String finish_reason;
  private MultiModalMessage message;
}
