package com.litongjava.openai.chat;

import com.litongjava.gemini.GeminiUsageMetadataVo;

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

  public ChatResponseUsage(GeminiUsageMetadataVo usageMetadata) {
    this.prompt_tokens = usageMetadata.getPromptTokenCount();
    this.completion_tokens = usageMetadata.getCandidatesTokenCount();
    this.total_tokens = usageMetadata.getTotalTokenCount();
    this.prompt_cache_hit_tokens = usageMetadata.getCachedContentTokenCount();
  }
}
