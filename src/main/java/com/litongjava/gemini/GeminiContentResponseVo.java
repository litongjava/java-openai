package com.litongjava.gemini;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选的内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiContentResponseVo {
  private List<GeminiPartVo> parts;
  private String role; // "model"
}
