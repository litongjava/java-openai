package com.litongjava.claude;

import java.util.List;

import com.litongjava.openai.chat.ChatResponseDelta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeChatResponse {
  private String id;
  private String type;
  private String role;
  private String model;
  private String stop_reason;
  private String stop_sequence;
  private ClaudeChatUsage usage;
  private List<ClaudeMessageContent> content;
  private ChatResponseDelta delta;
  private String rawResponse;
}
