package com.litongjava.openai.chat;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestFunctionParameter {
  private String type;
  private Map<String, ChatRequestFunctionProperty> properties;
  private List<String> required;
}
