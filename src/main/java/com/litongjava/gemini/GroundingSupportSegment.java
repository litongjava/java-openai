package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroundingSupportSegment {
  private Integer endIndex;
  private String text;
}
