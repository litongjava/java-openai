package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatSendArgs {
  private Long id;
  private String type;
  private String name;
  private String institution;
  private String url;
  private String[] urls;
}
