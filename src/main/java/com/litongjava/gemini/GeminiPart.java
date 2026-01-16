package com.litongjava.gemini;

import com.litongjava.chat.ChatImageFile;

/**
 * 对话的一段内容 既可以是 text，也可以是 image 等
 */
public class GeminiPart {
  private String text;
  private GeminiInlineData inlineData;
  private GeminiFileData fileData;
  private GeminiFunctionCallVo functionCall;

  public GeminiPart(String text) {
    this.text = text;
  }

  public GeminiPart(GeminiInlineData inlineData) {
    this.inlineData = inlineData;
  }

  public GeminiPart(GeminiFileData fileData) {
    this.fileData = fileData;
  }

  public GeminiPart(ChatImageFile chatFile) {
    String type = chatFile.getMimeType();
    String data = chatFile.getData();
    String url = chatFile.getUrl();
    if (data != null) {
      this.inlineData = new GeminiInlineData(type, data);
    } else if (url != null) {
      this.fileData = new GeminiFileData(type, url);
    }
  }

  public GeminiPart() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiPart(String text, GeminiInlineData inlineData, GeminiFileData fileData,
      GeminiFunctionCallVo functionCall) {
    super();
    this.text = text;
    this.inlineData = inlineData;
    this.fileData = fileData;
    this.functionCall = functionCall;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public GeminiInlineData getInlineData() {
    return inlineData;
  }

  public void setInlineData(GeminiInlineData inlineData) {
    this.inlineData = inlineData;
  }

  public GeminiFileData getFileData() {
    return fileData;
  }

  public void setFileData(GeminiFileData fileData) {
    this.fileData = fileData;
  }

  public GeminiFunctionCallVo getFunctionCall() {
    return functionCall;
  }

  public void setFunctionCall(GeminiFunctionCallVo functionCall) {
    this.functionCall = functionCall;
  }

}
