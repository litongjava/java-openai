package com.litongjava.google.search;

public class SearchUrl {
  private String type;
  private String template;

  public SearchUrl() {
    super();
    // TODO Auto-generated constructor stub
  }

  public SearchUrl(String type, String template) {
    super();
    this.type = type;
    this.template = template;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

}
