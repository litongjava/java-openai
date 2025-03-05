package com.litongjava.apify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkedinProfilePicDimension {
  private Integer width;
  private Integer height;
  private String url;
}
