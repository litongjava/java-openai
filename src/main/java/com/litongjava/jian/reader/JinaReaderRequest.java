package com.litongjava.jian.reader;

public class JinaReaderRequest {
  // 请求URL
  private String endpoint;
  // Headers
  private String authorization;
  private String xRespondWith;
  private String contentType;
  private String xRetainImages;
  private String xWithGeneratedAlt;
  private String xWithLinksSummary;

  private String url;

  public JinaReaderRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public JinaReaderRequest(String endpoint, String authorization, String xRespondWith, String contentType,
      String xRetainImages, String xWithGeneratedAlt, String xWithLinksSummary, String url) {
    super();
    this.endpoint = endpoint;
    this.authorization = authorization;
    this.xRespondWith = xRespondWith;
    this.contentType = contentType;
    this.xRetainImages = xRetainImages;
    this.xWithGeneratedAlt = xWithGeneratedAlt;
    this.xWithLinksSummary = xWithLinksSummary;
    this.url = url;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getAuthorization() {
    return authorization;
  }

  public void setAuthorization(String authorization) {
    this.authorization = authorization;
  }

  public String getxRespondWith() {
    return xRespondWith;
  }

  public void setxRespondWith(String xRespondWith) {
    this.xRespondWith = xRespondWith;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getxRetainImages() {
    return xRetainImages;
  }

  public void setxRetainImages(String xRetainImages) {
    this.xRetainImages = xRetainImages;
  }

  public String getxWithGeneratedAlt() {
    return xWithGeneratedAlt;
  }

  public void setxWithGeneratedAlt(String xWithGeneratedAlt) {
    this.xWithGeneratedAlt = xWithGeneratedAlt;
  }

  public String getxWithLinksSummary() {
    return xWithLinksSummary;
  }

  public void setxWithLinksSummary(String xWithLinksSummary) {
    this.xWithLinksSummary = xWithLinksSummary;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
