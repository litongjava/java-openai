package com.litongjava.bailian.image;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiModalConversationInput {
  private List<MultiModalMessage> messages;

  public static MultiModalConversationInput build(String text) {
    MultiModalMessage message = MultiModalMessage.build(text);
    List<MultiModalMessage> messages=new ArrayList<>(1);
    messages.add(message);
    return new MultiModalConversationInput(messages);
  }
}
