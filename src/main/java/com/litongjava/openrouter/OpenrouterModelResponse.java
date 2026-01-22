package com.litongjava.openrouter;

import java.util.List;

public class OpenrouterModelResponse {
  private List<OpenrouterPalmyraX5> data;

  public OpenrouterModelResponse() {
    super();
  }

  public OpenrouterModelResponse(List<OpenrouterPalmyraX5> data) {
    super();
    this.data = data;
  }

  public List<OpenrouterPalmyraX5> getData() {
    return data;
  }

  public void setData(List<OpenrouterPalmyraX5> data) {
    this.data = data;
  }
}
