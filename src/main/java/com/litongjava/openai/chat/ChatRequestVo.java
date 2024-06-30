package com.litongjava.openai.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestVo {
  private String model;
  private boolean stream;
  private List<ChatMessage> messages;
  private List<ChatRequestTool> tools;
  private Integer max_tokens;
}
