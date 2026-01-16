package com.litongjava.chat;

public class ChatImageFile {
  private String mimeType = "image/png";
  // data:image base64 code, url:image http url
  private String data, url;
  private boolean cached;

  public ChatImageFile(String url) {
    this.url = url;
  }

  public static ChatImageFile url(String url) {
    return new ChatImageFile(url);
  }

  public static ChatImageFile base64(String encodeImage) {
    ChatImageFile chatFile = new ChatImageFile();
    chatFile.setData(encodeImage);
    return chatFile;
  }

  public static ChatImageFile base64(String mimeType, String encodeImage) {
    ChatImageFile chatFile = new ChatImageFile();
    chatFile.setMimeType(mimeType);
    chatFile.setData(encodeImage);
    return chatFile;
  }

  public ChatImageFile() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ChatImageFile(String mimeType, String data, String url, boolean cached) {
    super();
    this.mimeType = mimeType;
    this.data = data;
    this.url = url;
    this.cached = cached;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isCached() {
    return cached;
  }

  public void setCached(boolean cached) {
    this.cached = cached;
  }

}
