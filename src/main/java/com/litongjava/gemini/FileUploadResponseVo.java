package com.litongjava.gemini;

public class FileUploadResponseVo {
  private FileUploadVo file;

  public FileUploadVo getFile() {
    return file;
  }

  public void setFile(FileUploadVo file) {
    this.file = file;
  }

  public String getUri() {
    return this.getFile().getUri();
  }
}
