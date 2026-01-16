package com.litongjava.gemini;

import java.time.Instant;

public class FileUploadVo {
  private String name;
  private String mimeType;
  private Long sizeBytes;
  private Instant createTime;
  private Instant updateTime;
  private Instant expirationTime;
  private String sha256Hash;
  private String uri;
  private String state;
  private String source;

  public FileUploadVo() {
    super();
    // TODO Auto-generated constructor stub
  }

  public FileUploadVo(String name, String mimeType, Long sizeBytes, Instant createTime, Instant updateTime,
      Instant expirationTime, String sha256Hash, String uri, String state, String source) {
    super();
    this.name = name;
    this.mimeType = mimeType;
    this.sizeBytes = sizeBytes;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.expirationTime = expirationTime;
    this.sha256Hash = sha256Hash;
    this.uri = uri;
    this.state = state;
    this.source = source;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public Long getSizeBytes() {
    return sizeBytes;
  }

  public void setSizeBytes(Long sizeBytes) {
    this.sizeBytes = sizeBytes;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Instant createTime) {
    this.createTime = createTime;
  }

  public Instant getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Instant updateTime) {
    this.updateTime = updateTime;
  }

  public Instant getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(Instant expirationTime) {
    this.expirationTime = expirationTime;
  }

  public String getSha256Hash() {
    return sha256Hash;
  }

  public void setSha256Hash(String sha256Hash) {
    this.sha256Hash = sha256Hash;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

}
