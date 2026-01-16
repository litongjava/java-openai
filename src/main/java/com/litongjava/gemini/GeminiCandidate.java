package com.litongjava.gemini;

/**
 * 候选答案
 */
public class GeminiCandidate {
  private Integer index;
  private GeminiContentResponse content;
  private String finishReason;
  private Double avgLogprobs;
  private GroundingMetadata groundingMetadata;

  public GeminiCandidate() {
    super();
  }

  public GeminiCandidate(Integer index, GeminiContentResponse content, String finishReason, Double avgLogprobs,
      GroundingMetadata groundingMetadata) {
    super();
    this.index = index;
    this.content = content;
    this.finishReason = finishReason;
    this.avgLogprobs = avgLogprobs;
    this.groundingMetadata = groundingMetadata;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public GeminiContentResponse getContent() {
    return content;
  }

  public void setContent(GeminiContentResponse content) {
    this.content = content;
  }

  public String getFinishReason() {
    return finishReason;
  }

  public void setFinishReason(String finishReason) {
    this.finishReason = finishReason;
  }

  public Double getAvgLogprobs() {
    return avgLogprobs;
  }

  public void setAvgLogprobs(Double avgLogprobs) {
    this.avgLogprobs = avgLogprobs;
  }

  public GroundingMetadata getGroundingMetadata() {
    return groundingMetadata;
  }

  public void setGroundingMetadata(GroundingMetadata groundingMetadata) {
    this.groundingMetadata = groundingMetadata;
  }

}
