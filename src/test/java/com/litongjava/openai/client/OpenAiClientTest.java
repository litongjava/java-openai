package com.litongjava.openai.client;

import java.util.List;

import org.junit.Test;

import nexus.io.cloudflare.CloudflareModelEntity;
import nexus.io.cloudflare.CloudflareModelInfo;
import nexus.io.openai.client.OpenAiClient;
import nexus.io.tio.utils.environment.EnvUtils;

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
