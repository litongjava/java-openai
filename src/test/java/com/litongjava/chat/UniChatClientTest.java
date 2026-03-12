package com.litongjava.chat;

import java.util.List;

import org.junit.Test;

import com.litongjava.claude.AnthropicModels;
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

  @Test
  public void useExchangeTokenClaude() {
    EnvUtils.load();
    PlatformInput platformInput = new PlatformInput(ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC,
        AnthropicModels.CLAUDE_SONNET_4_6);
    UniChatRequest request = new UniChatRequest(platformInput);
    request.setUserPrompts("how are you?");

    UniChatResponse response = UniChatClient.generate(request);
    String content = response.getMessage().getContent();
    System.out.println(content);
  }
}
