package com.litongjava.exchangetoken;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.chat.PlatformInput;
import com.litongjava.chat.UniChatClient;
import com.litongjava.chat.UniChatMessage;
import com.litongjava.chat.UniChatRequest;
import com.litongjava.chat.UniChatResponse;
import com.litongjava.consts.ModelPlatformName;
import com.litongjava.tio.utils.environment.EnvUtils;

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
