package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroundingSupport {
  private GroundingSupportSegment segment;
  private List<Integer> groundingChunkIndices;
}
