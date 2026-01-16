package com.litongjava.google.search;

import java.util.List;

public class Queries {
  private List<Query> request;
  private List<Query> nextPage;

  public Queries() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Queries(List<Query> request, List<Query> nextPage) {
    super();
    this.request = request;
    this.nextPage = nextPage;
  }

  public List<Query> getRequest() {
    return request;
  }

  public void setRequest(List<Query> request) {
    this.request = request;
  }

  public List<Query> getNextPage() {
    return nextPage;
  }

  public void setNextPage(List<Query> nextPage) {
    this.nextPage = nextPage;
  }

}
