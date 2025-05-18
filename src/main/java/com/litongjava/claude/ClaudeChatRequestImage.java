package com.litongjava.claude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeChatRequestImage {
  private String type;
  private String media_type;
  private String data;
  private String url;
}