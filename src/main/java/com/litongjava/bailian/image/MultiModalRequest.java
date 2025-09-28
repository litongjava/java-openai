package com.litongjava.bailian.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MultiModalRequest {
  private String model;
  private MultiModalConversationInput input;
  private MultiModalConversationParam parameters;

  public static MultiModalRequest build(String text) {
    MultiModalConversationInput input = MultiModalConversationInput.build(text);
    MultiModalConversationParam parameters = new MultiModalConversationParam();
    return build(input, parameters);
  }

  public static MultiModalRequest build(MultiModalConversationInput input, MultiModalConversationParam parameters) {
    return new MultiModalRequest("qwen-image-plus", input, parameters);
  }

}
