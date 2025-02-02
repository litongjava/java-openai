package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchReturnImage {
  private String image_url;
  private String origin_url;
  private Integer height;
  private Integer width;
}
