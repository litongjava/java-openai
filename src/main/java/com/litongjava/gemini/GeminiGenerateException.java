package com.litongjava.gemini;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeminiGenerateException extends RuntimeException {

  private static final long serialVersionUID = -4841623914325044141L;
  private String message;
  private String urlPerfix;
  private String requestJson;
  private int code;
  private String responseBody;

  public GeminiGenerateException(String message, String urlPerfix, String requestJson, int code, String responseBody) {
    this.message = message;
    this.urlPerfix = urlPerfix;
    this.requestJson = requestJson;
    this.code = code;
    this.responseBody = responseBody;
  }

}
