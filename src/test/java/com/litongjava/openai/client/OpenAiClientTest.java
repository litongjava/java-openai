package com.litongjava.openai.client;

import java.util.List;

import org.junit.Test;

import com.litongjava.cloudflare.CloudflareModelEntity;
import com.litongjava.cloudflare.CloudflareModelInfo;
import com.litongjava.tio.utils.environment.EnvUtils;

public class OpenAiClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    CloudflareModelInfo models = OpenAiClient.models();
    List<CloudflareModelEntity> list = models.getData();
    for (CloudflareModelEntity e : list) {
      System.out.println(e.getId());
    }
  }

}
