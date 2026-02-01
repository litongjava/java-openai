package com.litongjava.openai.client;

import org.junit.Test;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;

public class OpenAiClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    ResponseVo models = OpenAiClient.models();
    System.out.println(models.getBodyString());
  }

}
