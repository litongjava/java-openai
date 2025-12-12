package com.litongjava.openai.chat;

import com.litongjava.claude.ClaudeChatUsage;
import com.litongjava.gemini.GeminiUsageMetadata;

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

  //only use of claude
  private Integer cache_creation_input_tokens;
  private Integer cache_read_input_tokens;

  public ChatResponseUsage(GeminiUsageMetadata usageMetadata) {
    this.prompt_tokens = usageMetadata.getPromptTokenCount();
    this.completion_tokens = usageMetadata.getCandidatesTokenCount();
    this.total_tokens = usageMetadata.getTotalTokenCount();
    this.prompt_cache_hit_tokens = usageMetadata.getCachedContentTokenCount();
  }

  public ChatResponseUsage(ClaudeChatUsage usage) {
    this.prompt_tokens = usage.getInput_tokens();
    this.completion_tokens = usage.getOutput_tokens();
    this.total_tokens = prompt_tokens + completion_tokens;
    this.cache_creation_input_tokens = usage.getCache_creation_input_tokens();
    this.cache_read_input_tokens = usage.getCache_read_input_tokens();
    
  }
}
