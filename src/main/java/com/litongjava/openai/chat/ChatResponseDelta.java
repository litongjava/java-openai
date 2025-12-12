package com.litongjava.openai.chat;

import java.util.List;

import com.litongjava.gemini.GeminiPart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDelta {

  private String role;
  private String content;
  private String reasoning_content;
  private String refusal;
  private ChatFunctionCall function_call;

  // claude
  private String type;
  private String text;

  public ChatResponseDelta(String role, String content) {
    this.role = role;
    this.content = content;
  }

  public String getContent() {
    if (this.content != null) {
      return content;
    } else {
      return text;
    }
  }

  public ChatResponseDelta(String role, List<GeminiPart> parts) {
    for (GeminiPart geminiPartVo : parts) {
      String text = geminiPartVo.getText();
      if (text != null) {
        this.content = text;
      }
//      GeminiInlineDataVo inlineData = geminiPartVo.getInlineData();
//      if (inlineData != null) {
//        String mimeType = inlineData.getMimeType();
//        String data = inlineData.getData();
//        ChatImageFile chatImageFile = new ChatImageFile(data);
//
//        UniChatFile uniChatFile = new UniChatFile();
//        uniChatFile.setType(mimeType);
//        uniChatFile.setImage_url(chatImageFile);
//
//        List<UniChatFile> images = new ArrayList<>(1);
//        images.add(uniChatFile);
//      }
    }
  }
}
