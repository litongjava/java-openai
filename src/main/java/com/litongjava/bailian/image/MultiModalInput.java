package com.litongjava.bailian.image;

import java.util.ArrayList;
import java.util.List;

public class MultiModalInput {
  private List<MultiModalMessage> messages;

  public static MultiModalInput build(String text) {
    MultiModalMessage message = MultiModalMessage.build(text);
    List<MultiModalMessage> messages = new ArrayList<>(1);
    messages.add(message);
    return new MultiModalInput(messages);
  }

  public MultiModalInput() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalInput(List<MultiModalMessage> messages) {
    super();
    this.messages = messages;
  }

  public List<MultiModalMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<MultiModalMessage> messages) {
    this.messages = messages;
  }
}
