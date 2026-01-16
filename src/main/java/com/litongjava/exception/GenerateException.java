package com.litongjava.exception;

public class GenerateException extends RuntimeException {

  private static final long serialVersionUID = -4841623914325044141L;
  private String provider;
  private String message;
  private String urlPerfix;
  private String requestBody;
  private Integer statusCode;
  private String responseBody;

  public GenerateException(String provider, String message, String urlPerfix, String requestBody, Integer code,
      String responseBody) {
    this.provider = provider;
    this.message = message;
    this.urlPerfix = urlPerfix;
    this.requestBody = requestBody;
    this.statusCode = code;
    this.responseBody = responseBody;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUrlPerfix() {
    return urlPerfix;
  }

  public void setUrlPerfix(String urlPerfix) {
    this.urlPerfix = urlPerfix;
  }

  public String getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(String requestBody) {
    this.requestBody = requestBody;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
