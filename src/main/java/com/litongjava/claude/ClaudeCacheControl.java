package com.litongjava.claude;

public class ClaudeCacheControl {
  // 缓存的最短生存时间（TTL）为 5 分钟。目前，“ephemeral”是唯一支持的缓存类型，对应于这个 5 分钟的最短生命周期。
  private String type = "ephemeral";

  public ClaudeCacheControl() {
    super();
  }

  public ClaudeCacheControl(String type) {
    super();
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
