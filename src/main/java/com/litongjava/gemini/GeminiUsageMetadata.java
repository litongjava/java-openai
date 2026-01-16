package com.litongjava.gemini;

import java.util.List;

/**
 * UsageMetadata
 */
public class GeminiUsageMetadata {
  private int promptTokenCount;
  private int candidatesTokenCount;
  private int totalTokenCount;
  private int cachedContentTokenCount;
  private int thoughtsTokenCount;
  private List<GeminipTokensDetail> promptTokensDetails;
  private List<GeminipTokensDetail> cacheTokensDetails;

  public GeminiUsageMetadata() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiUsageMetadata(int promptTokenCount, int candidatesTokenCount, int totalTokenCount,
      int cachedContentTokenCount, int thoughtsTokenCount, List<GeminipTokensDetail> promptTokensDetails,
      List<GeminipTokensDetail> cacheTokensDetails) {
    super();
    this.promptTokenCount = promptTokenCount;
    this.candidatesTokenCount = candidatesTokenCount;
    this.totalTokenCount = totalTokenCount;
    this.cachedContentTokenCount = cachedContentTokenCount;
    this.thoughtsTokenCount = thoughtsTokenCount;
    this.promptTokensDetails = promptTokensDetails;
    this.cacheTokensDetails = cacheTokensDetails;
  }

  public int getPromptTokenCount() {
    return promptTokenCount;
  }

  public void setPromptTokenCount(int promptTokenCount) {
    this.promptTokenCount = promptTokenCount;
  }

  public int getCandidatesTokenCount() {
    return candidatesTokenCount;
  }

  public void setCandidatesTokenCount(int candidatesTokenCount) {
    this.candidatesTokenCount = candidatesTokenCount;
  }

  public int getTotalTokenCount() {
    return totalTokenCount;
  }

  public void setTotalTokenCount(int totalTokenCount) {
    this.totalTokenCount = totalTokenCount;
  }

  public int getCachedContentTokenCount() {
    return cachedContentTokenCount;
  }

  public void setCachedContentTokenCount(int cachedContentTokenCount) {
    this.cachedContentTokenCount = cachedContentTokenCount;
  }

  public int getThoughtsTokenCount() {
    return thoughtsTokenCount;
  }

  public void setThoughtsTokenCount(int thoughtsTokenCount) {
    this.thoughtsTokenCount = thoughtsTokenCount;
  }

  public List<GeminipTokensDetail> getPromptTokensDetails() {
    return promptTokensDetails;
  }

  public void setPromptTokensDetails(List<GeminipTokensDetail> promptTokensDetails) {
    this.promptTokensDetails = promptTokensDetails;
  }

  public List<GeminipTokensDetail> getCacheTokensDetails() {
    return cacheTokensDetails;
  }

  public void setCacheTokensDetails(List<GeminipTokensDetail> cacheTokensDetails) {
    this.cacheTokensDetails = cacheTokensDetails;
  }

}
