package com.litongjava.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebPageSource {
  private String pageContent;
  private WebPageMetadata metadata; 
}
