package com.litongjava.jian.rerank;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class RerankReqVo {
  private String model;
  private String query;
  private Integer top_n;
  private List<String> documents;
}
