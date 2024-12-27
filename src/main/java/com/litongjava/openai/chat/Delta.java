package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delta {
  private String role;
  private String content;
  private ChatFunctionCall function_call;
  private String refusal;
}
