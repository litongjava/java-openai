package com.litongjava.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JSON-RPC 2.0 Notification（无 id）
 * 例如：notifications/initialized
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpJsonRpcNotification<T> {
  private String jsonrpc = "2.0";
  private String method;
  private T params; // 通常为 null
}
