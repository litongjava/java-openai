package com.litongjava.claude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeChatUsage {
  private Integer input_tokens;
  private Integer output_tokens;
  private Integer cache_creation_input_tokens;
  private Integer cache_read_input_tokens;
}
