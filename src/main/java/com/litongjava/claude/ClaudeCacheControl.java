package com.litongjava.claude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeCacheControl {
  // 缓存的最短生存时间（TTL）为 5 分钟。目前，“ephemeral”是唯一支持的缓存类型，对应于这个 5 分钟的最短生命周期。
  private String type = "ephemeral";
}
