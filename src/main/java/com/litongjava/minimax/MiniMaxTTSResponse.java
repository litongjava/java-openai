package com.litongjava.minimax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniMaxTTSResponse {
  private MiniMaxResponseData data;
  private MiniMaxExtraInfo extra_info;
  private String trace_id;
  private MiniMaxBaseResp base_resp;
}