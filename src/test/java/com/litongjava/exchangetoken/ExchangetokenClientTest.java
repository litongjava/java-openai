package com.litongjava.exchangetoken;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;

import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.exchangetoken.ExchangetokenModels;

public class ExchangetokenClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    PlatformInput platformInput = new PlatformInput(ModelPlatformName.EXCHANGE_TOKEN,
        ExchangetokenModels.GEMINI_2_5_FLASH);
    UniChatRequest uniChatRequest = new UniChatRequest(platformInput);
    List<UniChatMessage> messages = new ArrayList<UniChatMessage>();
    messages.add(UniChatMessage.buildUser("just say hi"));
    uniChatRequest.setMessages(messages);

    UniChatResponse uniChatResponse = UniChatClient.generate(uniChatRequest);
    String content = uniChatResponse.getMessage().getContent();
    System.out.println(content);
  }
}
