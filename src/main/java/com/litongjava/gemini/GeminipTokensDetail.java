package com.litongjava.gemini;

public class GeminipTokensDetail {
  private String modality;
  private int tokenCount;

  public GeminipTokensDetail() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminipTokensDetail(String modality, int tokenCount) {
    super();
    this.modality = modality;
    this.tokenCount = tokenCount;
  }

  public String getModality() {
    return modality;
  }

  public void setModality(String modality) {
    this.modality = modality;
  }

  public int getTokenCount() {
    return tokenCount;
  }

  public void setTokenCount(int tokenCount) {
    this.tokenCount = tokenCount;
  }

}
