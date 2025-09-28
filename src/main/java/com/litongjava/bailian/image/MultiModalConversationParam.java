package com.litongjava.bailian.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiModalConversationParam {
  private String negative_prompt;
  private boolean prompt_extend;
  private boolean watermark = false;
  private String size = ImageSize.SQUARE_1328;
}
