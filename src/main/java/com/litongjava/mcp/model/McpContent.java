package com.litongjava.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class McpContent {
  private String type; // "text", "image", "video", etc.

  // 用于 text 类型
  private String text;

  // 用于媒体类型（image/video/audio）
  private String data; // Base64 编码数据
  private String uri; // 可选：远程地址或本地访问路径
  private String mimeType; // 媒体类型 MIME，如 "image/png", "video/mp4"

  public McpContent(String type, String text) {
    this.type = type;
    this.text = text;
  }

  public static McpContent buildText(String text) {
    return new McpContent("text", text);
  }

}
