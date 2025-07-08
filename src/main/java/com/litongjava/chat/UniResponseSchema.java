package com.litongjava.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniResponseSchema {
  private String type = "object";
  private Map<String, UniResponseProperty> properties;
  private List<String> required;

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
  public static UniResponseSchema array(String key) {
    UniResponseProperty itemProp = new UniResponseProperty("string");
    UniResponseProperty arrayProp = new UniResponseProperty("array", itemProp);
    Map<String, UniResponseProperty> properties = new HashMap<>();
    properties.put(key, arrayProp);

    UniResponseSchema schema = new UniResponseSchema();
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
  public static UniResponseSchema pythonCode() {
    Map<String, UniResponseProperty> properties = new HashMap<>();
    properties.put("action", new UniResponseProperty("string"));
    properties.put("tool", new UniResponseProperty("string"));
    properties.put("code", new UniResponseProperty("string"));
    UniResponseSchema schema = new UniResponseSchema();
    schema.setProperties(properties);
    return schema;
  }

  private static Map<String, String> itemType(String value) {
    Map<String, String> items = new HashMap<>();
    items.put("type", value);
    return items;
  }
}
