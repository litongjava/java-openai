package com.litongjava.jian.rerank;

import java.util.List;

public class RerankReqVo {
  private String model;
  private String query;
  private Integer top_n;
  private List<String> documents;

  public RerankReqVo() {
    super();
    // TODO Auto-generated constructor stub
  }

  public RerankReqVo(String model, String query, Integer top_n, List<String> documents) {
    super();
    this.model = model;
    this.query = query;
    this.top_n = top_n;
    this.documents = documents;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public Integer getTop_n() {
    return top_n;
  }

  public void setTop_n(Integer top_n) {
    this.top_n = top_n;
  }

  public List<String> getDocuments() {
    return documents;
  }

  public void setDocuments(List<String> documents) {
    this.documents = documents;
  }

}
