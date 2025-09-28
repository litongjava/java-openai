package com.litongjava.bailian.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MultiModalResponse {
  private String rawResponse;
  private String request_id;
  private MultiModalUsage usage;
  private MultiModalOutput output;
}
