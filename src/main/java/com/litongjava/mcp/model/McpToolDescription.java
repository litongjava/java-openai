package com.litongjava.mcp.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * tools/list 返回的工具描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class McpToolDescription {
  private String name;
  private String title; // 可选：便于 UI 展示
  private String description;
  private Map<String, Object> inputSchema; // JSON Schema (通常 type=object)
  private Map<String, Object> outputSchema; // 可选：如有明确的输出结构
}
