package com.litongjava.vo;

public class CodeBlock {
  private String type;
  private String code;

  public CodeBlock(String type, String code) {
    this.type = type;
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "CodeBlock[type=" + type + ", code=" + code.replace("\n", "\\n") + "]";
  }
}