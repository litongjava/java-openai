package com.litongjava.mcp.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tools/call 的 params 包含工具名、参数、以及可选的 _meta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpToolsCallParams {
  private String name;
  private Map<String, Object> arguments;

  private Map<String, Object> _meta; // 客户端透传的上下文（可选）
}
