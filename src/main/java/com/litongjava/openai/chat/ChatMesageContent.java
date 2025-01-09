package com.litongjava.openai.chat;

import com.litongjava.tio.utils.encoder.Base64Utils;
import com.litongjava.tio.utils.http.ContentTypeUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMesageContent {
  private String type = "text";
  private String text;
  private ChatRequestImage image_url;

  public ChatMesageContent(ChatRequestImage chatRequestImage) {
    this.type = "image_url";
    this.image_url = chatRequestImage;
  }

  public ChatMesageContent(String text) {
    this.text = text;
  }

  public ChatMesageContent(byte[] bytes, String suffix) {
    String mimeType = ContentTypeUtils.getContentType(suffix);
    String byteArrayToAltBase64 = Base64Utils.encodeImage(bytes, mimeType);

    ChatRequestImage chatRequestImage = new ChatRequestImage();
    chatRequestImage.setDetail("auto");
    chatRequestImage.setUrl(byteArrayToAltBase64);

    this.type = "image_url";
    this.image_url = chatRequestImage;
  }

}
