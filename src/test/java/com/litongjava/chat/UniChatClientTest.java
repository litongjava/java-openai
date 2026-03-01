package com.litongjava.chat;

import java.util.List;

import org.junit.Test;

import com.litongjava.consts.ModelPlatformName;
import com.litongjava.tio.utils.environment.EnvUtils;

public class UniChatClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    ChatModelResponse response = UniChatClient.models(ModelPlatformName.AIAPI);
    List<ChatModelEntity> data = response.getData();
    for (ChatModelEntity chatModelEntity : data) {
      System.out.println(chatModelEntity.getId());
    }
  }

}
