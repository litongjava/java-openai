package com.litongjava.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * initialize.params.clientInfo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpClientInfo {
  private String name;
  private String version;
}
