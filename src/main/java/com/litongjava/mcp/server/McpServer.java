package com.litongjava.mcp.server;

import com.litongjava.mcp.context.McpRequestContext;
import com.litongjava.mcp.exception.McpRpcException;
import com.litongjava.mcp.model.McpInitializeParams;
import com.litongjava.mcp.model.McpInitializeResult;
import com.litongjava.mcp.model.McpToolsCallParams;
import com.litongjava.mcp.model.McpToolsCallResult;
import com.litongjava.mcp.model.McpToolsListResult;

/**
 * 核心服务端接口（无反射）：你的 McpHandler 在协议各阶段直接调用这些方法。 开发者实现本接口即可对接 MCP 协议： initialize ->
 * 初始化握手 notificationsInitialized -> 客户端确认可接收事件 listTools -> 列出工具 callTool ->
 * 调用工具 shutdown -> （可选）优雅关闭 sessionClosed -> （可选）会话结束清理
 */
public interface McpServer {

  /**
   * 对应: method = "initialize"
   * 
   * @param params 初始化入参（协议版本、客户端能力、clientInfo）
   * @param ctx    请求上下文（会话、头、远端等）
   * @return 需要返回 serverInfo / capabilities / protocolVersion
   * @throws McpRpcException 业务或协商失败时抛出，Handler 会包装成 JSON-RPC error
   */
  McpInitializeResult initialize(McpInitializeParams params, McpRequestContext ctx) throws McpRpcException;

  /**
   * 对应: method = "notifications/initialized" 无入参；表示客户端已准备好接收事件（SSE）。
   * 若需要，可在此处开始推送后台任务事件、预热资源等。
   */
  void notificationsInitialized(McpRequestContext ctx) throws McpRpcException;

  /**
   * 对应: method = "tools/list"
   * 
   * @return 工具清单（name/title/description/inputSchema...）
   */
  McpToolsListResult listTools(McpRequestContext ctx) throws McpRpcException;

  /**
   * 对应: method = "tools/call"
   * 
   * @param params 工具名 + arguments（以及可选 _meta 已在模型中）
   * @return 工具结果（content[], isError, _meta）
   * @throws McpRpcException 工具不存在、校验失败或执行异常时抛出
   */
  McpToolsCallResult callTool(McpToolsCallParams params, McpRequestContext ctx) throws McpRpcException;

  /**
   * （可选）对应: method = "shutdown"（如实现该语义） 用于优雅关闭、刷新缓冲、释放资源等。
   */
  default void shutdown(McpRequestContext ctx) throws McpRpcException {
    /* no-op */ }

  /**
   * （可选）HTTP DELETE /mcp 等触发的会话结束回调 可在这里清理 session 级别缓存、临时文件等。
   */
  default void sessionClosed(McpRequestContext ctx) {
    /* no-op */ }
}
