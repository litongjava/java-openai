package com.litongjava.chat;

import java.util.List;
import java.util.Map;

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

  public UniResponseProperty() {
    super();
  }

  public UniResponseProperty(String type, String description, UniResponseProperty items,
      Map<String, UniResponseProperty> properties, List<String> required) {
    super();
    this.type = type;
    this.description = description;
    this.items = items;
    this.properties = properties;
    this.required = required;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UniResponseProperty getItems() {
    return items;
  }

  public void setItems(UniResponseProperty items) {
    this.items = items;
  }

  public Map<String, UniResponseProperty> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, UniResponseProperty> properties) {
    this.properties = properties;
  }

  public List<String> getRequired() {
    return required;
  }

  public void setRequired(List<String> required) {
    this.required = required;
  }

}
