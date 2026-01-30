package com.litongjava.cloudflare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudflareModelEntity {
  private String id;
  private String object;
  private Long created_at;
  private Double cost_in;
  private Double cost_out;
  private String owned_by;
}
