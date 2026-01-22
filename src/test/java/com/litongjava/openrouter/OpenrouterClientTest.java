package com.litongjava.openrouter;

import java.util.List;

import org.junit.Test;

public class OpenrouterClientTest {

  @Test
  public void test() {
    OpenrouterModelResponse models = new OpenrouterClient().getModels();
    List<OpenrouterPalmyraX5> data = models.getData();
    for (OpenrouterPalmyraX5 openrouterPalmyraX5 : data) {
      System.out.println(openrouterPalmyraX5.getId());
    }
  }

}
