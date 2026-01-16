package com.litongjava.gemini;

public class GeminiFileData {
  private String mimeType;
  private String fileUri;

  public GeminiFileData() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiFileData(String mimeType, String fileUri) {
    super();
    this.mimeType = mimeType;
    this.fileUri = fileUri;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getFileUri() {
    return fileUri;
  }

  public void setFileUri(String fileUri) {
    this.fileUri = fileUri;
  }

}
