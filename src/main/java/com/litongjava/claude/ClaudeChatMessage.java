package com.litongjava.claude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeChatMessage {
  private String type;
  private String text;
  private ClaudeCacheControl cache_control;

  public ClaudeChatMessage(String type, String text) {
    this.type = type;
    this.text = text;
  }
}
