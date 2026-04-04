package nexus.io.mcp.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tools/list 的 result
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpToolsListResult {
  private List<McpToolDescription> tools;
}
