package com.litongjava.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatFile {
  private String mimeType = "image/png";
  //data:image base64 code, url:image http url
  private String data, url;
  private boolean cached;

  public ChatFile(String url) {
    this.url = url;
  }

  public static ChatFile url(String url) {
    return new ChatFile(url);
  }

  public static ChatFile base64(String encodeImage) {
    ChatFile chatFile = new ChatFile();
    chatFile.setData(encodeImage);
    return chatFile;
  }

  public static ChatFile base64(String mimeType, String encodeImage) {
    ChatFile chatFile = new ChatFile();
    chatFile.setMimeType(mimeType);
    chatFile.setData(encodeImage);
    return chatFile;
  }
}
