package com.litongjava.gemini;

/**
 * 安全策略 例如: { "category": "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold":
 * "BLOCK_ONLY_HIGH" }
 */
public class GeminiSafetySetting {
  private String category;
  private String threshold;

  public GeminiSafetySetting() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiSafetySetting(String category, String threshold) {
    super();
    this.category = category;
    this.threshold = threshold;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getThreshold() {
    return threshold;
  }

  public void setThreshold(String threshold) {
    this.threshold = threshold;
  }

}
