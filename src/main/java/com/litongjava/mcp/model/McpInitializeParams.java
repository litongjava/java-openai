package com.litongjava.mcp.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * initialize 请求的 params
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpInitializeParams {
  private String protocolVersion;
  private Map<String, Object> capabilities; // 客户端声明的能力
  private McpClientInfo clientInfo;
}
