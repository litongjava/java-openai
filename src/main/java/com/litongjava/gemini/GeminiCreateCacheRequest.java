package com.litongjava.gemini; // Adjust package as needed

import java.util.List;

/**
 * 请求体 for POST /v1beta/cachedContents
 */

public class GeminiCreateCacheRequest {
  private String model = "models/gemini-2.5-pro";
  private List<GeminiContent> contents;
  private GeminiSystemInstruction systemInstruction;
  private String ttl = "300s";
  private String displayName; // Optional display name

  public GeminiCreateCacheRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiCreateCacheRequest(String model, List<GeminiContent> contents, GeminiSystemInstruction systemInstruction,
      String ttl, String displayName) {
    super();
    this.model = model;
    this.contents = contents;
    this.systemInstruction = systemInstruction;
    this.ttl = ttl;
    this.displayName = displayName;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public List<GeminiContent> getContents() {
    return contents;
  }

  public void setContents(List<GeminiContent> contents) {
    this.contents = contents;
  }

  public GeminiSystemInstruction getSystemInstruction() {
    return systemInstruction;
  }

  public void setSystemInstruction(GeminiSystemInstruction systemInstruction) {
    this.systemInstruction = systemInstruction;
  }

  public String getTtl() {
    return ttl;
  }

  public void setTtl(String ttl) {
    this.ttl = ttl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

}