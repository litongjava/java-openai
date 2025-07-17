package com.litongjava.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateException extends RuntimeException {

  private static final long serialVersionUID = -4841623914325044141L;
  private String provider;
  private String message;
  private String urlPerfix;
  private String requestJson;
  private Integer statusCode;
  private String responseBody;

  public GenerateException(String provider, String message, String urlPerfix, String requestJson, Integer code, String responseBody) {
    this.provider = provider;
    this.message = message;
    this.urlPerfix = urlPerfix;
    this.requestJson = requestJson;
    this.statusCode = code;
    this.responseBody = responseBody;
  }
}
