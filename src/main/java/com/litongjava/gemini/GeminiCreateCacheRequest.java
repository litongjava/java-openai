package com.litongjava.gemini; // Adjust package as needed

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求体 for POST /v1beta/cachedContents
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiCreateCacheRequest {
  private String model = "models/gemini-2.5-pro";
  private List<GeminiContent> contents;
  private GeminiSystemInstruction systemInstruction;
  private String ttl = "300s";
  private String displayName; // Optional display name
}