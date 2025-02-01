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
  private Integer prompt_cache_hit_tokens;
  private Integer prompt_cache_miss_tokens;
  private ChatPromptTokensDetails prompt_tokens_details;
  private ChatCompletionTokensDetails completion_tokens_details;
  
}
