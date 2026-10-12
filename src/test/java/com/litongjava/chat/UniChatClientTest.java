package com.litongjava.chat;

import java.util.List;

import org.junit.Test;

import nexus.io.chat.ChatModelEntity;
import nexus.io.chat.ChatModelResponse;
import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.deepseek.DeepSeekModels;
import nexus.io.exception.GenerateException;
import nexus.io.exchangetoken.ExchangetokenModels;
import nexus.io.gemini.GoogleModels;
import nexus.io.tio.utils.environment.EnvUtils;

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
    PlatformInput platformInput = new PlatformInput(ModelPlatformName.EXCHANGE_TOKEN,
        ExchangetokenModels.GPT_5_3_CODEX);
    UniChatRequest request = new UniChatRequest(platformInput);
    request.setUserPrompts("how are you?");

    try {
      UniChatResponse response = UniChatClient.generate(request);
      String content = response.getMessage().getContent();
      System.out.println(content);
    } catch (GenerateException e) {
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

  @Test
  public void deepseekSearch() {
    EnvUtils.load();
    UniChatResponse response = UniChatClient.webSearch(ModelPlatformName.DEEPSEEK, DeepSeekModels.deepseek_v4_flash,
        "查询 Java 最新正式版本，提供官方来源");
    System.out.println(response.getMessage().getContent());
    System.out.println(response.getCitations());
    response.getSearchSources().forEach(source -> System.out.println(source.getTitle() + " " + source.getUrl()));
    System.out.println("停止原因：" + response.getFinishReason());
    System.out.println("搜索错误：" + response.getSearchErrors());
  }
}
