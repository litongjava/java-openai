package com.litongjava.mcp.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.litongjava.mcp.model.McpContent;
import com.litongjava.mcp.model.McpToolDescription;

/**
 * MCP工具注册中心
 */
public class McpToolRegistry {
  private final Map<String, RegisteredTool> tools = new LinkedHashMap<>();

  /**
   * 注册工具
   */
  public McpToolRegistry register(String name, RegisteredTool tool) {
    tools.put(name, tool);
    return this;
  }

  public McpToolRegistry register(RegisteredTool tool) {
    tools.put(tool.getName(), tool);
    return this;
  }

  /**
   * 获取所有工具描述
   */
  public List<McpToolDescription> getToolDescriptions() {
    List<McpToolDescription> list = new ArrayList<>();
    for (RegisteredTool tool : tools.values()) {
      McpToolDescription desc = new McpToolDescription();
      desc.setName(tool.getName()).setTitle(tool.getTitle()).setDescription(tool.getDescription())
          .setInputSchema(tool.getInputSchema());
      list.add(desc);
    }
    return list;
  }

  /**
   * 调用工具
   */
  public List<McpContent> callTool(String name, Map<String, Object> arguments) {
    RegisteredTool tool = tools.get(name);
    if (tool == null) {
      throw new IllegalArgumentException("Unknown tool: " + name);
    }
    return tool.getHandler().apply(arguments);
  }

  /**
   * 创建工具构建器
   */
  public static ToolBuilder builder(String name) {
    return new ToolBuilder(name);
  }

}