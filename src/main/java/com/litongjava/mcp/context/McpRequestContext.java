package com.litongjava.mcp.context;

import java.util.Map;

/**
 * 每次调用的上下文信息（可按需扩展） 你的 McpHandler 在收到请求时构造并传入，避免业务层去碰 HTTP 层。
 */
public class McpRequestContext {
  private final String sessionId; // 来自请求头: Mcp-Session-Id
  private final String protocolVersion; // 来自请求头: Mcp-Protocol-Version
  private final String remoteAddress; // 远端地址（代理后可取 X-Forwarded-For）
  private final Map<String, String> headers; // 原始请求头（只读拷贝或不可变）

  public McpRequestContext(String sessionId, String protocolVersion, String remoteAddress,
      Map<String, String> headers) {
    this.sessionId = sessionId;
    this.protocolVersion = protocolVersion;
    this.remoteAddress = remoteAddress;
    this.headers = headers;
  }

  public String getSessionId() {
    return sessionId;
  }

  public String getProtocolVersion() {
    return protocolVersion;
  }

  public String getRemoteAddress() {
    return remoteAddress;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  /** 便捷：获取某个请求头（大小写不敏感可在 Handler 里统一处理后放入） */
  public String getHeader(String name) {
    return headers == null ? null : headers.get(name);
  }
}
