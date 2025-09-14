package com.litongjava.openai.chat;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.chat.ChatImageFile;
import com.litongjava.chat.UniChatFile;
import com.litongjava.gemini.GeminiInlineDataVo;
import com.litongjava.gemini.GeminiPartVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  public ChatResponseMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  public ChatResponseMessage(String role, GeminiPartVo geminiPartVo) {
    this.role = role;
    this.content = geminiPartVo.getText();
  }

  public ChatResponseMessage(String role, List<GeminiPartVo> parts) {
    this.role = role;
    for (GeminiPartVo geminiPartVo : parts) {
      String text = geminiPartVo.getText();
      if (text != null) {
        this.content = text;
      }
      GeminiInlineDataVo inlineData = geminiPartVo.getInlineData();
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
