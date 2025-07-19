package com.litongjava.openai.chat;

import com.litongjava.tio.utils.base64.Base64Utils;
import com.litongjava.tio.utils.http.ContentTypeUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageContent {
  private String type = "text";
  private String text;
  private ChatRequestImage image_url;

  public ChatMessageContent(ChatRequestImage chatRequestImage) {
    this.type = "image_url";
    this.image_url = chatRequestImage;
  }

  public ChatMessageContent(String text) {
    this.text = text;
  }

  public ChatMessageContent(byte[] bytes, String suffix) {
    String mimeType = ContentTypeUtils.getContentType(suffix);
    String byteArrayToAltBase64 = Base64Utils.encodeImage(bytes, mimeType);

    ChatRequestImage chatRequestImage = new ChatRequestImage();
    chatRequestImage.setDetail("auto");
    chatRequestImage.setUrl(byteArrayToAltBase64);

    this.type = "image_url";
    this.image_url = chatRequestImage;
  }

}
