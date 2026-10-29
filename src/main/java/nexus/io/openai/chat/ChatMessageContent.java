package nexus.io.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nexus.io.chat.ChatImageFile;
import nexus.io.tio.utils.base64.Base64Utils;
import nexus.io.tio.utils.http.ContentTypeUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageContent {
  private String type = "text";
  private String text;
  private ChatRequestImage image_url;
  private ChatRequestVideo video_url;

  public ChatMessageContent(ChatRequestImage chatRequestImage) {
    this.type = "image_url";
    this.image_url = chatRequestImage;
  }

  public ChatMessageContent(ChatRequestVideo chatRequestVideo) {
    this.type = "video_url";
    this.video_url = chatRequestVideo;
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

  public ChatMessageContent(ChatImageFile chatImageFile) {
    String url = chatImageFile.getData() != null ? chatImageFile.getData() : chatImageFile.getUrl();
    String mimeType = chatImageFile.getMimeType();
    if (mimeType != null && mimeType.startsWith("video/")) {
      this.type = "video_url";
      this.video_url = new ChatRequestVideo(url);
      return;
    }
    this.type = "image_url";
    ChatRequestImage chatRequestImage = new ChatRequestImage();
    chatRequestImage.setDetail("auto");
    chatRequestImage.setUrl(url);
    this.image_url = chatRequestImage;
  }
}
