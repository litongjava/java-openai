package com.litongjava.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniImageData {
  private String b64_json;
  private String url;
  private String type;
}
