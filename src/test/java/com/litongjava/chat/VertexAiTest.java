package com.litongjava.chat;

import org.junit.Test;

import com.litongjava.consts.ModelPlatformName;
import com.litongjava.exception.GenerateException;
import com.litongjava.gemini.GoogleModels;
import com.litongjava.tio.utils.environment.EnvUtils;

public class VertexAiTest {

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
