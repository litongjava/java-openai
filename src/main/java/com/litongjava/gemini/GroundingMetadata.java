package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroundingMetadata {
  private SearchEntryPoint searchEntryPoint;
  private List<GroundingChunk> groundingChunks;
  private List<GroundingSupport> groundingSupports;
  private List<String> webSearchQueries;
}
