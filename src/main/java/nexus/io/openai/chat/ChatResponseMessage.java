package nexus.io.openai.chat;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nexus.io.chat.ChatImageFile;
import nexus.io.chat.UniChatFile;
import nexus.io.chat.UniSources;
import nexus.io.gemini.GeminiInlineData;
import nexus.io.gemini.GeminiPart;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseMessage {

  private String role;
  private String content;
  private String refusal;
  private String reasoning;
  private String reasoning_content;
  private List<ToolCall> tool_calls;
  private List<UniChatFile> images;
  private UniSources uniSources;

  public ChatResponseMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  public ChatResponseMessage(String role, GeminiPart geminiPartVo) {
    this.role = role;
    this.content = geminiPartVo.getText();
  }

  public ChatResponseMessage(String role, List<GeminiPart> parts) {
    this.role = role;
    for (GeminiPart geminiPartVo : parts) {
      String text = geminiPartVo.getText();
      if (text != null) {
        this.content = text;
      }
      GeminiInlineData inlineData = geminiPartVo.getInlineData();
      if (inlineData != null) {
        String mimeType = inlineData.getMimeType();
        String data = inlineData.getData();
        ChatImageFile chatImageFile = new ChatImageFile(data);

        UniChatFile uniChatFile = new UniChatFile();
        uniChatFile.setType(mimeType);
        uniChatFile.setImage_url(chatImageFile);

        List<UniChatFile> images = new ArrayList<>(1);
        images.add(uniChatFile);
        this.images = images;
      }
    }

  }

}
