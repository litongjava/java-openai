package com.litongjava.gemini;

import java.util.List;

public class GroundingSupport {
  private GroundingSupportSegment segment;
  private List<Integer> groundingChunkIndices;

  public GroundingSupport() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GroundingSupport(GroundingSupportSegment segment, List<Integer> groundingChunkIndices) {
    super();
    this.segment = segment;
    this.groundingChunkIndices = groundingChunkIndices;
  }

  public GroundingSupportSegment getSegment() {
    return segment;
  }

  public void setSegment(GroundingSupportSegment segment) {
    this.segment = segment;
  }

  public List<Integer> getGroundingChunkIndices() {
    return groundingChunkIndices;
  }

  public void setGroundingChunkIndices(List<Integer> groundingChunkIndices) {
    this.groundingChunkIndices = groundingChunkIndices;
  }

}
