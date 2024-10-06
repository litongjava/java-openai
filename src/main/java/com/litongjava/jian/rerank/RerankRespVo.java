package com.litongjava.jian.rerank;

import java.util.List;

import com.litongjava.openai.chat.ChatResponseUsage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class RerankRespVo {
  private String model;
  private ChatResponseUsage usage;
  private List<RerankResult> results;
}
