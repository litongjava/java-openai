package com.litongjava.chat;

import java.util.List;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.monitor.SystemMonitorUtils;

import nexus.io.chat.ChatModelEntity;
import nexus.io.chat.ChatModelResponse;
import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.claude.AnthropicModels;
import nexus.io.consts.ModelPlatformName;
import nexus.io.exception.GenerateException;
import nexus.io.exchangetoken.ExchangetokenModels;
import nexus.io.gemini.GoogleModels;

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
  public void useExchangeToken() {
    EnvUtils.load();
    PlatformInput platformInput = new PlatformInput(ModelPlatformName.EXCHANGE_TOKEN, ExchangetokenModels.GPT_5_3_CODEX);
    UniChatRequest request = new UniChatRequest(platformInput);
    request.setUserPrompts("how are you?");

    try {
      UniChatResponse response = UniChatClient.generate(request);
      String content = response.getMessage().getContent();
      System.out.println(content);
    }catch (GenerateException e) {
      System.out.println(e.getResponseBody());
    }
    
  }

  @Test
  public void useExchangeTokenClaude() {
    EnvUtils.load();
    PlatformInput platformInput = new PlatformInput(ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC,
        ExchangetokenModels.CLAUDE_SONNET_4_6);
    UniChatRequest request = new UniChatRequest(platformInput);
    request.setUserPrompts("how are you?");

    UniChatResponse response = UniChatClient.generate(request);
    String content = response.getMessage().getContent();
    System.out.println(content);
  }

  @Test
  public void useVertexAi() {
    EnvUtils.load();
    PlatformInput platformInput = new PlatformInput(ModelPlatformName.VERTEX_AI, GoogleModels.GEMINI_2_5_FLASH);

    UniChatRequest request = new UniChatRequest(platformInput);
    request.setUserPrompts("how are you?");

    try {
      UniChatResponse response = UniChatClient.generate(request);
      String content = response.getMessage().getContent();
      System.out.println(content);
    } catch (GenerateException e) {
      String responseBody = e.getResponseBody();
      System.out.println(responseBody);
    }
  }
}
