package com.litongjava.openai.client;

import java.util.List;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;

import nexus.io.cloudflare.CloudflareModelEntity;
import nexus.io.cloudflare.CloudflareModelInfo;
import nexus.io.openai.client.OpenAiClient;

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
