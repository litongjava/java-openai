package com.litongjava.gemini;

import java.util.List;

import com.litongjava.chat.UniResponseSchema;
import com.litongjava.chat.UniThinkingConfig;

/**
 * 文本生成配置 例如: "generationConfig": { "stopSequences": ["Title"], "temperature":
 * 0, "maxOutputTokens": 800, "topP": 0.8, "topK": 10 }
 */
public class GeminiGenerationConfig {
  private Float temperature;
  private Integer topK;
  private Double topP;
  private Integer maxOutputTokens;
  private List<String> stopSequences;
  private List<String> responseModalities;
  private String responseMimeType;
  private UniResponseSchema responseSchema;
  private UniThinkingConfig thinkingConfig;

  public GeminiGenerationConfig buildJsonValue() {
    this.responseMimeType = "application/json";
    return this;
  }

  public GeminiGenerationConfig() {
    super();
  }

  public GeminiGenerationConfig(Float temperature, Integer topK, Double topP, Integer maxOutputTokens,
      List<String> stopSequences, List<String> responseModalities, String responseMimeType,
      UniResponseSchema responseSchema, UniThinkingConfig thinkingConfig) {
    super();
    this.temperature = temperature;
    this.topK = topK;
    this.topP = topP;
    this.maxOutputTokens = maxOutputTokens;
    this.stopSequences = stopSequences;
    this.responseModalities = responseModalities;
    this.responseMimeType = responseMimeType;
    this.responseSchema = responseSchema;
    this.thinkingConfig = thinkingConfig;
  }

  public Float getTemperature() {
    return temperature;
  }

  public void setTemperature(Float temperature) {
    this.temperature = temperature;
  }

  public Integer getTopK() {
    return topK;
  }

  public void setTopK(Integer topK) {
    this.topK = topK;
  }

  public Double getTopP() {
    return topP;
  }

  public void setTopP(Double topP) {
    this.topP = topP;
  }

  public Integer getMaxOutputTokens() {
    return maxOutputTokens;
  }

  public void setMaxOutputTokens(Integer maxOutputTokens) {
    this.maxOutputTokens = maxOutputTokens;
  }

  public List<String> getStopSequences() {
    return stopSequences;
  }

  public void setStopSequences(List<String> stopSequences) {
    this.stopSequences = stopSequences;
  }

  public List<String> getResponseModalities() {
    return responseModalities;
  }

  public void setResponseModalities(List<String> responseModalities) {
    this.responseModalities = responseModalities;
  }

  public String getResponseMimeType() {
    return responseMimeType;
  }

  public void setResponseMimeType(String responseMimeType) {
    this.responseMimeType = responseMimeType;
  }

  public UniResponseSchema getResponseSchema() {
    return responseSchema;
  }

  public void setResponseSchema(UniResponseSchema responseSchema) {
    this.responseSchema = responseSchema;
  }

  public UniThinkingConfig getThinkingConfig() {
    return thinkingConfig;
  }

  public void setThinkingConfig(UniThinkingConfig thinkingConfig) {
    this.thinkingConfig = thinkingConfig;
  }

}
