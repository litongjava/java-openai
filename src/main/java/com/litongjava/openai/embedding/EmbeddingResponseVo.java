package com.litongjava.openai.embedding;

import java.util.List;

import com.litongjava.openai.chat.ChatResponseUsage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingResponseVo {
  private String object;
  private String model;
  private List<EmbeddingData> data;
  private ChatResponseUsage usage;
}
