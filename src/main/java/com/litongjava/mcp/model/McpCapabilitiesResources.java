package com.litongjava.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class McpCapabilitiesResources {
  private Boolean subscribe;
  private Boolean listChanged;
}
