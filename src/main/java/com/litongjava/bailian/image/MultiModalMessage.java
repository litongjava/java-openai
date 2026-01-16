package com.litongjava.bailian.image;

import java.util.ArrayList;
import java.util.List;

public class MultiModalMessage {

  private String role;
  private List<MultiModalContent> content;

  public static MultiModalMessage build(String text) {
    MultiModalContent multiModalContent = new MultiModalContent(text);
    List<MultiModalContent> content = new ArrayList<MultiModalContent>(2);
    content.add(multiModalContent);
    return new MultiModalMessage("user", content);
  }

  public MultiModalMessage() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalMessage(String role, List<MultiModalContent> content) {
    super();
    this.role = role;
    this.content = content;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<MultiModalContent> getContent() {
    return content;
  }

  public void setContent(List<MultiModalContent> content) {
    this.content = content;
  }

}
