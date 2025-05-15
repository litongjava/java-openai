package com.litongjava.claude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeChatMessage {
  private String type;
  private String content;
}
