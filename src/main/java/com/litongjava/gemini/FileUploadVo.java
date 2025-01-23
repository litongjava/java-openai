package com.litongjava.gemini;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
