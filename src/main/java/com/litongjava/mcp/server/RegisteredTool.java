package com.litongjava.mcp.server;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.litongjava.mcp.model.McpContent;

import lombok.Data;

/**
 * 已注册的工具
 */
@Data
public class RegisteredTool {
  private String name;
  private String title;
  private String description;
  private Map<String, Object> inputSchema;
  private Function<Map<String, Object>, List<McpContent>> handler;
}
