package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatRequestToolCallFunction {
  private String name;
  private String description;
  private ChatRequestFunctionParameter parameters;
  
}
