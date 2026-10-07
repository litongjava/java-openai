package nexus.io.chat;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import nexus.io.openai.chat.OpenAiChatRequest;
import nexus.io.openai.responses.OpenAiResponsesRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class ChatRequestUtilsTest {

  @Test
  public void incompatibleModelsOmitTemperatureFromBothRequestFormats() {
    String[] models = {"claude-opus-5-5", "claude-sonnet-5", "claude-opus-4-7", "claude-opus-4-8",
        "claude-fable-5", "claude-fable-5-1", "claude-fable-5_1", "claude-mythos-preview",
        "anthropic/claude-opus-5.5", "anthropic/claude-sonnet-5:thinking",
        "o1-preview", "o3-mini-2025-01-31", "openai/o4-mini", "gpt-5", "gpt-5-2025-08-07",
        "gpt-5-mini", "gpt-5-nano", "gpt-5.1-codex-max", "gpt-5.2-pro", "gpt-6-astra"};
    for (String model : models) {
      JSONObject chat = JSON.parseObject(ChatRequestUtils.toSkipNullJson(
          new OpenAiChatRequest().setModel(model).setTemperature(0.3f).setMax_tokens(123)));
      assertFalse(model, chat.containsKey("temperature"));
      assertEquals(model, chat.getString("model"));
      assertEquals(123, chat.getIntValue("max_tokens"));
      JSONObject responses = JSON.parseObject(ChatRequestUtils.toSkipNullJson(
          new OpenAiResponsesRequest().setModel(model).setTemperature(0.3f)));
      assertFalse(model, responses.containsKey("temperature"));
    }
  }

  @Test
  public void supportedAndUnknownModelsKeepTemperatureIncludingZero() {
    String[] models = {null, "", "claude-opus-4-6", "claude-sonnet-4-6", "claude-haiku-4-5",
        "gpt-4o", "gpt-5.1", "gpt-5.2-2025-12-11", "gpt-5.4", "gpt-5-chat-latest",
        "openai/gpt-5.2", "deepseek-chat", "gemini-2.5-pro", "custom-model", "o10", "custom/o3"};
    for (String model : models) {
      for (float temperature : new float[] {0f, 0.7f}) {
        JSONObject json = JSON.parseObject(ChatRequestUtils.toSkipNullJson(
            new OpenAiChatRequest().setModel(model).setTemperature(temperature)));
        assertEquals(model, temperature, json.getFloatValue("temperature"), 0f);
        assertTrue(model, json.containsKey("temperature"));
      }
    }
    assertNull(ChatRequestUtils.normalizeTemperature("gpt-4o", null));
    assertNull(ChatRequestUtils.normalizeTemperature("o3", null));
  }

  @Test
  public void ownedTransportAppliesRulesForNativeClaudeOpenAiAndResponses() throws Exception {
    String[][] routes = {{"anthropic", "claude-opus-5-5"}, {"openai", "o3"},
        {"openrouter", "anthropic/claude-sonnet-5"}, {"openai_responses", "gpt-5-mini"}};
    AtomicInteger calls = new AtomicInteger();
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
      calls.incrementAndGet();
      Buffer buffer = new Buffer();
      chain.request().body().writeTo(buffer);
      assertFalse(JSON.parseObject(buffer.readUtf8()).containsKey("temperature"));
      String body = "{\"role\":\"assistant\",\"content\":[{\"type\":\"text\",\"text\":\"ok\"}],"
          + "\"choices\":[{\"message\":{\"content\":\"ok\"}}],"
          + "\"output\":[{\"type\":\"message\",\"content\":[{\"type\":\"output_text\",\"text\":\"ok\"}]}]}";
      return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
          .code(200).message("OK").body(ResponseBody.create(body, MediaType.parse("application/json"))).build();
    }).build();
    for (String[] route : routes) {
      UniChatRequest request = new UniChatRequest(route[0], route[1])
          .setApiPrefixUrl("https://example.invalid/v1").setApiKey("test-key").setTemperature(0.3f)
          .setMessages(Collections.singletonList(new UniChatMessage("user", "hello")));
      UniChatClient.generate(client, request);
      assertEquals(Float.valueOf(0.3f), request.getTemperature());
    }
    assertEquals(routes.length, calls.get());
  }
}
