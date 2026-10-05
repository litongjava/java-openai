package com.litongjava.chat;

import static org.junit.Assert.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import com.sun.net.httpserver.HttpServer;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import nexus.io.consts.ModelPlatformName;

public class UniChatCompatibilityTest {
  @Test public void giteeGeneratePreservesThinkingFormatAndOutputLimit() throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    AtomicReference<JSONObject> sent = new AtomicReference<>();
    server.createContext("/v1/chat/completions", exchange -> {
      java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
      byte[] chunk = new byte[4096];
      int count;
      while ((count = exchange.getRequestBody().read(chunk)) != -1) {
        buffer.write(chunk, 0, count);
      }
      sent.set(JSON.parseObject(new String(buffer.toByteArray(), StandardCharsets.UTF_8)));
      byte[] response = "{\"model\":\"test\",\"choices\":[{\"finish_reason\":\"stop\",\"message\":{\"role\":\"assistant\",\"content\":\"ok\"}}]}".getBytes(StandardCharsets.UTF_8);
      exchange.getResponseHeaders().add("Content-Type", "application/json");
      exchange.sendResponseHeaders(200, response.length);
      exchange.getResponseBody().write(response);
      exchange.close();
    });
    server.start();
    try {
      UniChatRequest request = new UniChatRequest(ModelPlatformName.GITEE, "test");
      request.setApiPrefixUrl("http://127.0.0.1:" + server.getAddress().getPort() + "/v1").setApiKey("local-test")
          .setSystemPrompt("system").setMessages(Arrays.asList(new UniChatMessage("user", "question")))
          .setMax_tokens(900).setThinking(Collections.singletonMap("type", "disabled")).setResponseFormat("json_object");
      assertEquals("ok", UniChatClient.generate(request).getMessage().getContent());
      assertEquals("disabled", sent.get().getJSONObject("thinking").getString("type"));
      assertEquals("json_object", sent.get().getJSONObject("response_format").getString("type"));
      assertEquals(900, sent.get().getIntValue("max_tokens"));
    } finally {
      server.stop(0);
    }
  }

  @Test public void conversionCanBeRepeatedWithoutMutatingHistory() {
    UniChatMessage message = new UniChatMessage("model", "earlier answer");
    UniChatRequest request = new UniChatRequest(Arrays.asList(message)).setSystemPrompt("rules");
    assertEquals(2, UniChatClient.toOpenAiRequest(request).getMessages().size());
    assertEquals("assistant", UniChatClient.toOpenAiRequest(request).getMessages().get(1).getRole());
    assertEquals(1, request.getMessages().size());
    assertEquals("model", message.getRole());
  }
}
