package com.litongjava.gemini;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponseProperty {

  private String type;
  private Map<String, String> items;

  public GeminiResponseProperty(String type) {
    this.type = type;
  }
}
