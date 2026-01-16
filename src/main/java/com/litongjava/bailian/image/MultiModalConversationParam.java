package com.litongjava.bailian.image;

public class MultiModalConversationParam {
  private String negative_prompt;
  private boolean prompt_extend;
  private boolean watermark = false;
  private String size = ImageSize.SQUARE_1328;

  public MultiModalConversationParam() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalConversationParam(String negative_prompt, boolean prompt_extend, boolean watermark, String size) {
    super();
    this.negative_prompt = negative_prompt;
    this.prompt_extend = prompt_extend;
    this.watermark = watermark;
    this.size = size;
  }

  public String getNegative_prompt() {
    return negative_prompt;
  }

  public void setNegative_prompt(String negative_prompt) {
    this.negative_prompt = negative_prompt;
  }

  public boolean isPrompt_extend() {
    return prompt_extend;
  }

  public void setPrompt_extend(boolean prompt_extend) {
    this.prompt_extend = prompt_extend;
  }

  public boolean isWatermark() {
    return watermark;
  }

  public void setWatermark(boolean watermark) {
    this.watermark = watermark;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

}
