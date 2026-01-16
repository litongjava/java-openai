package com.litongjava.gemini;

import java.util.List;

/**
 * gemini接口响应体
 */

public class GeminiChatResponse {
  private List<GeminiCandidate> candidates;
  private GeminiUsageMetadata usageMetadata;
  private String modelVersion;
  private String rawData;

  public GeminiChatResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiChatResponse(List<GeminiCandidate> candidates, GeminiUsageMetadata usageMetadata, String modelVersion,
      String rawData) {
    super();
    this.candidates = candidates;
    this.usageMetadata = usageMetadata;
    this.modelVersion = modelVersion;
    this.rawData = rawData;
  }

  public List<GeminiCandidate> getCandidates() {
    return candidates;
  }

  public void setCandidates(List<GeminiCandidate> candidates) {
    this.candidates = candidates;
  }

  public GeminiUsageMetadata getUsageMetadata() {
    return usageMetadata;
  }

  public void setUsageMetadata(GeminiUsageMetadata usageMetadata) {
    this.usageMetadata = usageMetadata;
  }

  public String getModelVersion() {
    return modelVersion;
  }

  public void setModelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
  }

  public String getRawData() {
    return rawData;
  }

  public void setRawData(String rawData) {
    this.rawData = rawData;
  }
}
