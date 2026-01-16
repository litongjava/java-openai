package com.litongjava.gemini;

import java.util.List;

/**
 * 候选的内容
 */
public class GeminiContentResponse {
  private List<GeminiPart> parts;
  private String role; // "model"
  public GeminiContentResponse() {
    super();
    // TODO Auto-generated constructor stub
  }
  public GeminiContentResponse(List<GeminiPart> parts, String role) {
    super();
    this.parts = parts;
    this.role = role;
  }
  public List<GeminiPart> getParts() {
    return parts;
  }
  public void setParts(List<GeminiPart> parts) {
    this.parts = parts;
  }
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
  
}
