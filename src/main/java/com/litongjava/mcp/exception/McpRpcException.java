package com.litongjava.mcp.exception;

/**
 * 业务层抛出的异常，会被 McpHandler 捕获并转换为 JSON-RPC error： code/message/data ->
 * McpRpcError
 */
@SuppressWarnings("serial")
public class McpRpcException extends Exception {
  private final int code;
  private final Object data;

  public McpRpcException(int code, String message) {
    super(message);
    this.code = code;
    this.data = null;
  }

  public McpRpcException(int code, String message, Object data) {
    super(message);
    this.code = code;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public Object getData() {
    return data;
  }
}
