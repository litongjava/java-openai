package com.litongjava.apify;

import java.util.List;

public class LinkedinSkill {
  private String title;
  private List<LinkedinSubComponent> subComponents;

  public LinkedinSkill() {
    super();
    // TODO Auto-generated constructor stub
  }

  public LinkedinSkill(String title, List<LinkedinSubComponent> subComponents) {
    super();
    this.title = title;
    this.subComponents = subComponents;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<LinkedinSubComponent> getSubComponents() {
    return subComponents;
  }

  public void setSubComponents(List<LinkedinSubComponent> subComponents) {
    this.subComponents = subComponents;
  }

}
