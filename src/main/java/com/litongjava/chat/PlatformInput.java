package com.litongjava.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class PlatformInput {

  private String platform;
  private String model;

  public PlatformInput(String platform) {
    this.platform = platform;
  }
}
