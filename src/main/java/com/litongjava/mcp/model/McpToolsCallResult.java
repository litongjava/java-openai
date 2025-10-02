package com.litongjava.mcp.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tools/call 的 result content 是富内容数组；isError 指示是否出错；_meta 可返回附加信息（可选）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpToolsCallResult {
  private List<McpContent> content;
  private boolean isError;
  private Map<String, Object> _meta; // 可选
}
