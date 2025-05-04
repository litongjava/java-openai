package com.litongjava.chat;

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
  private ChatResponseMessage message;
  private ChatResponseUsage usage;
}
