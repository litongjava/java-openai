package com.litongjava.bailian.image;

public class MultiModalTaskMetric {
  private Integer FAILED, SUCCEEDED, TOTAL;

  public MultiModalTaskMetric() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalTaskMetric(Integer fAILED, Integer sUCCEEDED, Integer tOTAL) {
    super();
    FAILED = fAILED;
    SUCCEEDED = sUCCEEDED;
    TOTAL = tOTAL;
  }

  public Integer getFAILED() {
    return FAILED;
  }

  public void setFAILED(Integer fAILED) {
    FAILED = fAILED;
  }

  public Integer getSUCCEEDED() {
    return SUCCEEDED;
  }

  public void setSUCCEEDED(Integer sUCCEEDED) {
    SUCCEEDED = sUCCEEDED;
  }

  public Integer getTOTAL() {
    return TOTAL;
  }

  public void setTOTAL(Integer tOTAL) {
    TOTAL = tOTAL;
  }

}
