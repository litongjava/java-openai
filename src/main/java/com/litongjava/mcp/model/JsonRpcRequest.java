package com.litongjava.mcp.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonRpcRequest {
  private String jsonrpc;
  private String method;
  private Map<String,Object> params;
  private Object id;
}
