package com.litongjava.gemini;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponseSchema {
  private String type = "object";
  private Map<String, GeminiResponseProperty> properties;

  /**
    "responseSchema": {
      "type": "object",
      "properties": {
        "suggestions": {
          "type": "array",
          "items": {
            "type": "string"
          }
        }
      }
    }
   */
  public static GeminiResponseSchema array(String key) {
    Map<String, String> items = itemType("string");
    Map<String, GeminiResponseProperty> properties = new HashMap<>();
    properties.put(key, new GeminiResponseProperty("array", items));

    GeminiResponseSchema schema = new GeminiResponseSchema();
    schema.setProperties(properties);
    return schema;
  }

  /**
   *
    "responseSchema": {
      "type": "object",
      "properties": {
        "action": {
          "type": "string"
        },
        "tool": {
          "type": "string"
        }
        "code": {
          "type": "string"
        },
        
      }
    }
   * @return
   */
  public static GeminiResponseSchema pythonCode() {
    Map<String, GeminiResponseProperty> properties = new HashMap<>();
    properties.put("action", new GeminiResponseProperty("string"));
    properties.put("tool", new GeminiResponseProperty("string"));
    properties.put("code", new GeminiResponseProperty("string"));
    GeminiResponseSchema schema = new GeminiResponseSchema();
    schema.setProperties(properties);
    return schema;
  }

  private static Map<String, String> itemType(String value) {
    Map<String, String> items = new HashMap<>();
    items.put("type", value);
    return items;
  }
}
