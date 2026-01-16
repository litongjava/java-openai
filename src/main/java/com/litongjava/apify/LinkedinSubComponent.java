package com.litongjava.apify;

import java.util.List;

import dev.langchain4j.model.output.structured.Description;

public class LinkedinSubComponent {
  private List<Description> description;

  public LinkedinSubComponent() {
    super();
    // TODO Auto-generated constructor stub
  }

  public LinkedinSubComponent(List<Description> description) {
    super();
    this.description = description;
  }

  public List<Description> getDescription() {
    return description;
  }

  public void setDescription(List<Description> description) {
    this.description = description;
  }
}
