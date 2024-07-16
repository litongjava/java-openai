package com.litongjava.openai.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OpenAiChatRequestVo {
  private String model;
  private boolean stream;
  private List<ChatMessage> messages;
  private List<ChatRequestTool> tools;
  private Integer max_tokens;
}
