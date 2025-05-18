package com.litongjava.claude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeChatSource {
  private String type;
  private String media_type;
  private String data;
  private String url;

  public static ClaudeChatSource url(String url) {
    ClaudeChatSource claudeChatSource = new ClaudeChatSource();
    claudeChatSource.type = "url";
    claudeChatSource.url = url;
    return claudeChatSource;
  }

  public static ClaudeChatSource base64(String mimeType, String data) {
    ClaudeChatSource claudeChatSource = new ClaudeChatSource();
    claudeChatSource.type = "base64";
    claudeChatSource.media_type = mimeType;
    claudeChatSource.data = data;
    return claudeChatSource;
  }

}
