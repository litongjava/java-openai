package com.litongjava.genie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenieTTSRequest {

  private String character_name;
  private String text;
  private boolean split_sentence = true;

  public GenieTTSRequest(String character_name, String text) {
    this.character_name = character_name;
    this.text = text;
  }
}
