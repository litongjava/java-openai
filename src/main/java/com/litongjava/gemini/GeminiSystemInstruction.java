package com.litongjava.gemini;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示 system_instruction 结构 例如: { "system_instruction": { "parts": [ { "text":
 * "You are a helpful lighting system bot..." }
 * 
 * ] } }
 */
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

  public GeminiSystemInstruction() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiSystemInstruction(List<GeminiPart> parts) {
    super();
    this.parts = parts;
  }

  public List<GeminiPart> getParts() {
    return parts;
  }

  public void setParts(List<GeminiPart> parts) {
    this.parts = parts;
  }

}
