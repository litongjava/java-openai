package com.litongjava.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JsonRpcResponse<T> {
  private String jsonrpc = "2.0";
  private Object id;
  private T result;
  private RpcError error;
}
