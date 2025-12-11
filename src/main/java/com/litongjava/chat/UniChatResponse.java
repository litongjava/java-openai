package com.litongjava.chat;

import java.util.List;

import com.litongjava.openai.chat.ChatResponseDelta;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.ChatResponseUsage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UniChatResponse {

  private transient String rawData;
  private String model;
  private ChatResponseMessage message;
  private ChatResponseDelta delta;
  private ChatResponseUsage usage;
  private List<String> citations;

  public UniChatResponse(String model, ChatResponseMessage message, ChatResponseUsage usage, String rawData) {
    this.model = model;
    this.message = message;
    this.usage = usage;
    this.rawData = rawData;
  }
}
