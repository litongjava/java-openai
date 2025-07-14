package com.litongjava.linux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeRequest {
  private Long id;
  private String code;
  private Integer timeout;

  public CodeRequest(String code) {
    this.code = code;
  }
}
