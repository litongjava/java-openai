package com.litongjava.openai.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiChatResponseVo {
  private String id;
  private String object;
  private Long created;
  private String model;
  private String system_fingerprint;
  private List<Choice> choices;
  private ChatResponseUsage usage;
  private List<String> citations;
  private List<SearchReturnImage> images;
  private String rawResponse;
}
