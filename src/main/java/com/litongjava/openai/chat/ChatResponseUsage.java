package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseUsage {
  private Integer prompt_tokens;
  private Integer completion_tokens;
  private Integer total_tokens;
}
