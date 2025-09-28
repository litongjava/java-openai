package com.litongjava.bailian.image;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MultiModalMessage {
  
  private String role;
  private List<MultiModalContent> content;
  

  public static MultiModalMessage build(String text) {
    MultiModalContent multiModalContent = new MultiModalContent(text);
    List<MultiModalContent> content = new ArrayList<MultiModalContent>(2);
    content.add(multiModalContent);
    return new MultiModalMessage("user", content);
  }
}
