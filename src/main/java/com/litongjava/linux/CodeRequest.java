package com.litongjava.linux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CodeRequest {
  private Long id;
  private String code;
  private Integer timeout;

  public CodeRequest(String code) {
    this.code = code;
  }

  public CodeRequest(long id, String code) {
    this.id = id;
    this.code = code;
  }
}
