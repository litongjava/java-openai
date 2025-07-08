package com.litongjava.chat;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniResponseProperty {

  private String type;
  private String description;
  private UniResponseProperty items;
  private Map<String, UniResponseProperty> properties;
  private List<String> required;

  public UniResponseProperty(String type) {
    this.type = type;
  }

  public UniResponseProperty(String type, UniResponseProperty items) {
    this.type = type;
    this.items = items;
  }
}
