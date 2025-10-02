package com.litongjava.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * initialize 响应的 result
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpInitializeResult {
  private String protocolVersion;
  private McpCapabilities capabilities; // 服务器能力
  private McpServerInfo serverInfo;

  public McpInitializeResult(String protocolVersion, McpServerInfo serverInfo) {
    this.protocolVersion = protocolVersion;
    this.capabilities = new McpCapabilities();
    this.serverInfo = serverInfo;
  }

  public McpInitializeResult(String protocolVersion, String name, String version) {
    this.protocolVersion = protocolVersion;
    this.capabilities = new McpCapabilities();
    this.serverInfo = new McpServerInfo(name, version);
  }

  public static McpInitializeResult build(String protocolVersion, McpServerInfo serverInfo) {
    return new McpInitializeResult(protocolVersion, serverInfo);
  }

  public static McpInitializeResult build(String protocolVersion, String name, String version) {
    return new McpInitializeResult(protocolVersion, name, version);
  }

}
