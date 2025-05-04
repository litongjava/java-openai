package com.litongjava.openai.chat;

import java.util.List;

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
  private String reasoning_content;
  private List<ToolCall> tool_calls;

  public ChatResponseMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  public ChatResponseMessage(String role, GeminiPartVo geminiPartVo) {
    this.role = role;
    this.content = geminiPartVo.getText();
  }
}
