package com.litongjava.claude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeChatUsage {
  private int input_tokens;
  private int cache_creation_input_tokens;
  private int cache_read_input_tokens;
  private int output_tokens;
}
