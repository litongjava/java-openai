package com.litongjava.mcp.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class McpCapabilities {

  //private McpCapabilitiesExperimental experimental;
  private Map<String,Object> experimental;
  private McpCapabilitiesPrompts prompts;
  private McpCapabilitiesResources resources;
  private McpCapabilitiesTools tools;

  public McpCapabilities() {
    this.experimental = new HashMap<String, Object>(1);
    this.prompts = new McpCapabilitiesPrompts(false);
    this.resources = new McpCapabilitiesResources(false, false);
    this.tools = new McpCapabilitiesTools(false);
  }
}
