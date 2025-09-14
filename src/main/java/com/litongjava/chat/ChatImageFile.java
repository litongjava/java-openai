package com.litongjava.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatImageFile {
  private String mimeType = "image/png";
  //data:image base64 code, url:image http url
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
}
