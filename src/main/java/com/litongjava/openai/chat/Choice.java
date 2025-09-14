package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
  private Object logprobs;
  private Object finish_reason;
  private String native_finish_reason;
  private Integer index;
  private ChatResponseDelta delta;
  private ChatResponseMessage message;
}
