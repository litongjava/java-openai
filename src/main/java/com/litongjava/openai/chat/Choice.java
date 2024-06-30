package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
  private Integer index;
  private Delta delta;
  private Object logprobs;
  private Object finish_reason;
  private ChatResponseMessage message;
}
