package com.litongjava.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * initialize.result.serverInfo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpServerInfo {
  private String name;
  private String version;
}
