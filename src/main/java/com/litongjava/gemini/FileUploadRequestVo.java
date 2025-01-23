package com.litongjava.gemini;

public class FileUploadRequestVo {
  private FileVo file;

  public FileUploadRequestVo() {
    super();
  }

  public FileUploadRequestVo(FileVo file) {
    super();
    this.file = file;
  }

  public FileUploadRequestVo(String mineType) {
    super();
    this.file = new FileVo(mineType);
  }

  public FileVo getFile() {
    return file;
  }

  public void setFile(FileVo file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "FileUploadRequestVo [file=" + file + "]";
  }

  public static class FileVo {
    private String mimeType;

    public FileVo() {

    }

    public FileVo(String mimeType) {
      this.mimeType = mimeType;
    }

    @Override
    public String toString() {
      return "FileVo [mimeType=" + mimeType + "]";
    }

    public String getMimeType() {
      return mimeType;
    }

    public void setMimeType(String mimeType) {
      this.mimeType = mimeType;
    }

  }
}
