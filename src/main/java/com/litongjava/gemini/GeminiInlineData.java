package com.litongjava.gemini;

/**
 * 如果是图片等非文本内容，需要用 inline_data 封装
 */
public class GeminiInlineData {
  private String mimeType;
  private String data; // base64编码后的二进制

  public GeminiInlineData() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiInlineData(String mimeType, String data) {
    super();
    this.mimeType = mimeType;
    this.data = data;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

}
