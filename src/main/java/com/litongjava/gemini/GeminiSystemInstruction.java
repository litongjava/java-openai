package com.litongjava.gemini;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示 system_instruction 结构
 * 例如:
 * {
 *   "system_instruction": {
 *     "parts": [
 *      {
 *        "text": "You are a helpful lighting system bot..."
 *      }
 *       
 *     ]
 *   }
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiSystemInstruction {
  private List<GeminiPart> parts;

  public GeminiSystemInstruction(GeminiPart part) {
    this.parts = new ArrayList<>();
    parts.add(part);
  }

  public GeminiSystemInstruction(String systemPrompt) {
    GeminiPart part = new GeminiPart(systemPrompt);
    this.parts = new ArrayList<>();
    parts.add(part);
  }
}
