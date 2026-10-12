package nexus.io.chat;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import nexus.io.openai.chat.ChatRequestTool;
import nexus.io.search.WebSearchUserLocation;
import nexus.io.tio.utils.environment.EnvUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSourceListener;
import okio.Buffer;

/** All calls are intercepted; these tests do not use network access or account credentials. */
public class UniChatWebSearchTest {
  private static final String OPENAI = "{\"model\":\"actual-model\",\"status\":\"completed\",\"output\":["
      + "{\"type\":\"web_search_call\",\"action\":{\"type\":\"search\",\"sources\":[{\"url\":\"https://example.com/a\"},"
      + "{\"url\":\"https://example.com/b\",\"title\":\"Other\"}]}},"
      + "{\"type\":\"message\",\"content\":[{\"type\":\"output_text\",\"text\":\"One. \",\"annotations\":["
      + "{\"type\":\"url_citation\",\"url\":\"https://example.com/a\",\"title\":\"Source\"}]},"
      + "{\"type\":\"output_text\",\"text\":\"Two.\",\"annotations\":[{\"type\":\"url_citation\",\"url\":\"https://example.com/a\"}]}]}],"
      + "\"usage\":{\"input_tokens\":10,\"output_tokens\":5,\"total_tokens\":15}}";
  private static final String MESSAGES = "{\"model\":\"actual-model\",\"stop_reason\":\"end_turn\",\"content\":["
      + "{\"type\":\"text\",\"text\":\"One. \"},{\"type\":\"server_tool_use\",\"name\":\"web_search\"},"
      + "{\"type\":\"web_search_tool_result\",\"content\":[{\"type\":\"web_search_result\",\"url\":\"https://example.com/a\",\"encrypted_content\":\"opaque\"},"
      + "{\"type\":\"web_search_result\",\"url\":\"https://example.com/b\",\"title\":\"Other\"}]},"
      + "{\"type\":\"text\",\"text\":\"Two.\",\"citations\":[{\"url\":\"https://example.com/a\",\"title\":\"Source\"},"
      + "{\"url\":\"https://example.com/a\"}]}],\"usage\":{\"input_tokens\":10,\"output_tokens\":5,"
      + "\"server_tool_use\":{\"web_search_requests\":2}}}";

  private final AtomicReference<Request> sent = new AtomicReference<>();
  private final AtomicReference<JSONObject> body = new AtomicReference<>();
  private final AtomicInteger calls = new AtomicInteger();

  private OkHttpClient client(String response, int code) {
    return new OkHttpClient.Builder().retryOnConnectionFailure(false).addInterceptor(chain -> {
      calls.incrementAndGet();
      sent.set(chain.request());
      Buffer buffer = new Buffer();
      chain.request().body().writeTo(buffer);
      body.set(JSON.parseObject(buffer.readUtf8()));
      return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(code).message("test")
          .body(ResponseBody.create(response, MediaType.parse("application/json"))).build();
    }).build();
  }

  private UniChatRequest request(String platform, String model) {
    return new UniChatRequest(platform, model).setApiKey("request-key").setUserPrompts("Find current Java releases");
  }

  @Test
  public void infersPlatformAndUsesNativeDefaultEndpointsWithoutMutatingRequest() throws Exception {
    String[][] cases = {{"gpt-5.4", "https://api.openai.com/v1/responses"},
        {"anthropic/claude-sonnet-4-6", "https://api.anthropic.com/v1/messages"},
        {"deepseek-v4-flash", "https://api.deepseek.com/anthropic/v1/messages"}};
    for (String[] item : cases) {
      UniChatRequest request = request(null, item[0]);
      UniChatResponse response = UniChatClient.webSearch(client(item[0].startsWith("gpt") ? OPENAI : MESSAGES, 200), request);
      assertEquals(item[1], sent.get().url().toString());
      assertEquals(item[0].replace("anthropic/", ""), body.get().getString("model"));
      assertEquals("actual-model", response.getModel());
      assertEquals(item[0], request.getModel());
      assertNull(request.getPlatform());
      assertNull(request.getMax_tokens());
      assertNull(request.getApiPrefixUrl());
      assertNull(request.getWebSearchOptions());
    }
  }

  @Test
  public void fillsMissingModelsButPreservesExplicitAliases() throws Exception {
    String[][] cases = {{"openai", "gpt-5.4"}, {"openai_responses", "gpt-5.4"},
        {"claude", "claude-sonnet-4-6"}, {"deepseek", "deepseek-v4-flash"}};
    for (String[] item : cases) {
      UniChatRequest request = request(item[0], null);
      UniChatClient.webSearch(client(item[0].startsWith("openai") ? OPENAI : MESSAGES, 200), request);
      assertEquals(item[1], body.get().getString("model"));
      assertNull(request.getModel());
    }
    UniChatClient.webSearch(client(MESSAGES, 200), request("anthropic", "my-private-model"));
    assertEquals("my-private-model", body.get().getString("model"));
  }

  @Test
  public void translatesSearchOptionsAndOutputLimitsByProtocol() throws Exception {
    UniWebSearchOptions options = new UniWebSearchOptions().setMaxToolCalls(3)
        .setAllowedDomains(Collections.singletonList("openjdk.org"))
        .setUserLocation(new WebSearchUserLocation().setCountry("CN"));
    for (String platform : Arrays.asList("openai", "anthropic", "deepseek")) {
      UniChatRequest request = request(platform, null).setMax_tokens(1234).setWebSearchOptions(options);
      UniChatClient.webSearch(client("openai".equals(platform) ? OPENAI : MESSAGES, 200), request);
      JSONObject tool = body.get().getJSONArray("tools").getJSONObject(0);
      assertEquals("CN", tool.getJSONObject("user_location").getString("country"));
      if ("openai".equals(platform)) {
        assertEquals("web_search", tool.getString("type"));
        assertEquals(3, body.get().getIntValue("max_tool_calls"));
        assertEquals(1234, body.get().getIntValue("max_output_tokens"));
        assertEquals("required", body.get().getString("tool_choice"));
        assertEquals("openjdk.org", tool.getJSONObject("filters").getJSONArray("allowed_domains").getString(0));
        assertFalse(tool.containsKey("max_uses"));
      } else {
        assertEquals("web_search_20250305", tool.getString("type"));
        assertEquals(3, tool.getIntValue("max_uses"));
        assertEquals(1234, body.get().getIntValue("max_tokens"));
        assertEquals("openjdk.org", tool.getJSONArray("allowed_domains").getString(0));
        assertFalse(tool.containsKey("filters"));
        assertFalse(tool.containsKey("function"));
        assertFalse(body.get().containsKey("max_tool_calls"));
      }
    }
  }

  @Test
  public void preservesFalseLiveAccessAndOmitsUnsupportedTemperature() throws Exception {
    UniChatRequest request = request("openai", "gpt-6-astra").setTemperature(0.7f)
        .setWebSearchOptions(new UniWebSearchOptions().setExternalWebAccess(false).setSearchContextSize("low"));
    UniChatClient.webSearch(client(OPENAI, 200), request);
    assertFalse(body.get().containsKey("temperature"));
    JSONObject tool = body.get().getJSONArray("tools").getJSONObject(0);
    assertEquals(Boolean.FALSE, tool.getBoolean("external_web_access"));
    assertEquals("low", tool.getString("search_context_size"));
    assertEquals(Float.valueOf(0.7f), request.getTemperature());
    UniChatClient.webSearch(client(MESSAGES, 200), request("anthropic", "claude-opus-4-7").setTemperature(0.3f));
    assertFalse(body.get().containsKey("temperature"));
    UniChatClient.webSearch(client(MESSAGES, 200), request("deepseek", null).setTemperature(0.3f));
    assertEquals(0.3, body.get().getDoubleValue("temperature"), 0.00001);
  }

  @Test
  public void credentialsAndEndpointOverridesFollowPriorityAndKeepDeepSeekSearchSeparate() throws Exception {
    String[] names = {"OPENAI_API_URL", "OPENAI_API_KEY", "OPENAI_RESPONSES_API_URL", "OPENAI_RESPONSES_API_KEY",
        "CLAUDE_API_URL", "CLAUDE_API_KEY", "DEEPSEEK_API_URL", "DEEPSEEK_SEARCH_BASE_URL", "DEEPSEEK_API_KEY"};
    Map<String, String> old = new LinkedHashMap<>();
    for (String name : names) old.put(name, EnvUtils.get(name));
    try {
      EnvUtils.set("OPENAI_API_URL", "https://openai.example/v1");
      EnvUtils.set("OPENAI_API_KEY", "openai-key");
      EnvUtils.set("OPENAI_RESPONSES_API_URL", "");
      EnvUtils.set("OPENAI_RESPONSES_API_KEY", "");
      EnvUtils.set("CLAUDE_API_URL", "https://claude.example/v1");
      EnvUtils.set("CLAUDE_API_KEY", "claude-key");
      EnvUtils.set("DEEPSEEK_API_URL", "https://chat-only.example/v1");
      EnvUtils.set("DEEPSEEK_SEARCH_BASE_URL", "https://search.example/anthropic/v1/");
      EnvUtils.set("DEEPSEEK_API_KEY", "deepseek-key");
      UniChatClient.webSearch(client(OPENAI, 200), request("openai_responses", null).setApiKey(null));
      assertEquals("https://openai.example/v1/responses", sent.get().url().toString());
      assertEquals("Bearer openai-key", sent.get().header("Authorization"));
      EnvUtils.set("OPENAI_RESPONSES_API_URL", "https://responses.example/v1");
      EnvUtils.set("OPENAI_RESPONSES_API_KEY", "responses-key");
      UniChatClient.webSearch(client(OPENAI, 200), request("openai_responses", null).setApiKey(null));
      assertEquals("https://responses.example/v1/responses", sent.get().url().toString());
      assertEquals("Bearer responses-key", sent.get().header("Authorization"));
      UniChatClient.webSearch(client(MESSAGES, 200), request("anthropic", null).setApiKey(null));
      assertEquals("https://claude.example/v1/messages", sent.get().url().toString());
      assertEquals("claude-key", sent.get().header("x-api-key"));
      UniChatClient.webSearch(client(MESSAGES, 200), request("deepseek", null).setApiKey(null));
      assertEquals("https://search.example/anthropic/v1/messages", sent.get().url().toString());
      assertEquals("Bearer deepseek-key", sent.get().header("Authorization"));
      assertEquals("deepseek-key", sent.get().header("x-api-key"));
      UniChatRequest explicit = request("deepseek", null).setApiPrefixUrl("https://gateway.example/v1/");
      UniChatClient.webSearch(client(MESSAGES, 200), "explicit-key", explicit);
      assertEquals("https://gateway.example/v1/messages", sent.get().url().toString());
      assertEquals("explicit-key", sent.get().header("x-api-key"));
      assertEquals("request-key", explicit.getApiKey());
    } finally {
      for (Map.Entry<String, String> entry : old.entrySet()) EnvUtils.set(entry.getKey(), entry.getValue());
    }
  }

  @Test
  public void officialDeepSeekChatPrefixIsConvertedButExplicitGatewaysArePreserved() throws Exception {
    UniChatClient.webSearch(client(MESSAGES, 200), request("deepseek", null).setApiPrefixUrl("https://api.deepseek.com/v1"));
    assertEquals("https://api.deepseek.com/anthropic/v1/messages", sent.get().url().toString());
    UniChatClient.webSearch(client(MESSAGES, 200), request("deepseek", null).setApiPrefixUrl("https://custom.example/v1"));
    assertEquals("https://custom.example/v1/messages", sent.get().url().toString());
  }

  @Test
  public void preservesSystemHistoryAndCallerOwnedClientAcrossRepeatedCalls() throws Exception {
    UniChatMessage history = new UniChatMessage("model", "Earlier answer");
    UniChatMessage user = new UniChatMessage("Find new information");
    for (String platform : Arrays.asList("openai", "anthropic", "deepseek")) {
      UniChatRequest request = request(platform, null).setSystemPrompt("Answer in Chinese")
          .setMessages(Collections.unmodifiableList(Arrays.asList(history, user)));
      OkHttpClient client = client("openai".equals(platform) ? OPENAI : MESSAGES, 200);
      for (int i = 0; i < 2; i++) {
        UniChatClient.webSearch(client, request);
        if ("openai".equals(platform)) {
          assertEquals("system", body.get().getJSONArray("input").getJSONObject(0).getString("role"));
          assertEquals("assistant", body.get().getJSONArray("input").getJSONObject(1).getString("role"));
        } else {
          assertTrue(body.get().getJSONArray("system").toJSONString().contains("Answer in Chinese"));
          assertEquals("assistant", body.get().getJSONArray("messages").getJSONObject(0).getString("role"));
        }
        assertEquals("model", history.getRole());
        assertEquals(2, request.getMessages().size());
        assertNull(request.getModel());
      }
      assertFalse(client.retryOnConnectionFailure());
    }
    assertEquals(6, calls.get());
  }

  @Test
  public void unifiesAllTextBlocksDeduplicatesSourcesAndKeepsCitationsDistinct() throws Exception {
    for (String platform : Arrays.asList("openai", "anthropic", "deepseek")) {
      String fixture = "openai".equals(platform) ? OPENAI : MESSAGES;
      UniChatResponse response = UniChatClient.webSearch(client(fixture, 200), request(platform, null));
      assertEquals("One. Two.", response.getMessage().getContent());
      assertEquals(Collections.singletonList("https://example.com/a"), response.getCitations());
      assertEquals(2, response.getSearchSources().size());
      assertEquals("Source", response.getSearchSources().get(0).getTitle());
      assertEquals("Other", response.getSearchSources().get(1).getTitle());
      assertTrue(response.getSearchErrors().isEmpty());
      assertEquals(Integer.valueOf(15), response.getUsage().getTotal_tokens());
      assertEquals(fixture, response.getRawData());
      assertEquals("openai".equals(platform) ? "completed" : "end_turn", response.getFinishReason());
      if (!"openai".equals(platform)) assertEquals(Integer.valueOf(2), response.getWebSearchRequests());
    }
  }

  @Test
  public void toolErrorsAndPausedTurnsAreNotMisreportedAsMalformedResponses() throws Exception {
    String error = "{\"stop_reason\":\"pause_turn\",\"content\":[{\"type\":\"web_search_tool_result\",\"content\":{"
        + "\"type\":\"web_search_tool_result_error\",\"error_code\":\"max_uses_exceeded\"}}]}";
    UniChatResponse response = UniChatClient.webSearch(client(error, 200), request("deepseek", null));
    assertEquals("deepseek-v4-flash", response.getModel());
    assertEquals("pause_turn", response.getFinishReason());
    assertEquals(Collections.singletonList("max_uses_exceeded"), response.getSearchErrors());
    assertNull(response.getMessage().getContent());
    assertEquals(error, response.getRawData());
    assertEquals(1, calls.get());
    response = UniChatClient.webSearch(client("{\"status\":\"failed\",\"output\":[],\"error\":{\"code\":\"server_error\"}}", 200), request("openai", null));
    assertEquals("failed", response.getFinishReason());
    assertEquals(Collections.singletonList("server_error"), response.getSearchErrors());
  }

  @Test
  public void enableSearchReroutesGenerateButOrdinaryChatStillUsesChatCompletions() throws Exception {
    for (String platform : Arrays.asList("openai", "anthropic", "deepseek")) {
      UniChatClient.generate(client("openai".equals(platform) ? OPENAI : MESSAGES, 200), request(platform, null).setEnable_search(true));
      assertTrue(sent.get().url().encodedPath().endsWith("openai".equals(platform) ? "/responses" : "/messages"));
      assertNotNull(body.get().getJSONArray("tools"));
    }
    String chat = "{\"choices\":[{\"message\":{\"role\":\"assistant\",\"content\":\"ordinary\"}}]}";
    UniChatClient.generate(client(chat, 200), request("openai", "gpt-5.4").setEnable_search(false));
    assertEquals("/v1/chat/completions", sent.get().url().encodedPath());
    assertNull(body.get().get("tools"));
  }

  @Test
  public void explicitAnthropicRelayUsesItsOwnEndpointAndCredentials() throws Exception {
    String oldKey = EnvUtils.get("EXCHANGE_TOKEN_API_KEY");
    try {
      EnvUtils.set("EXCHANGE_TOKEN_API_KEY", "relay-key");
      UniChatClient.webSearch(client(MESSAGES, 200), request("exchange_token_anthropic", "claude-sonnet-4-6").setApiKey(null));
      assertEquals("https://api.exchangetoken.ai/v1/messages", sent.get().url().toString());
      assertEquals("relay-key", sent.get().header("x-api-key"));
    } finally {
      EnvUtils.set("EXCHANGE_TOKEN_API_KEY", oldKey);
    }
  }

  @Test
  public void invalidRoutingAndOptionsFailBeforeSending() throws Exception {
    UniChatRequest[] invalid = {request(null, "unknown-model"), request("google", "gemini-2.5-pro"),
        request("openai", "claude-sonnet-4-6"), request("anthropic", null).setStream(true),
        request("anthropic", null).setWebSearchOptions(new UniWebSearchOptions().setExternalWebAccess(false)),
        request("openai", null).setWebSearchOptions(new UniWebSearchOptions().setMaxToolCalls(0)),
        request("openai", null).setWebSearchOptions(new UniWebSearchOptions().setSearchContextSize("huge")),
        request("deepseek", null).setWebSearchOptions(new UniWebSearchOptions().setAllowedDomains(Collections.singletonList("a.com"))
            .setBlockedDomains(Collections.singletonList("b.com"))),
        request("deepseek", null).setMessages(Collections.emptyList())};
    for (UniChatRequest request : invalid) {
      try {
        UniChatClient.webSearch(client(MESSAGES, 200), request);
        fail("Expected validation error");
      } catch (IllegalArgumentException expected) {
        assertNotNull(expected.getMessage());
      }
    }
    UniChatRequest withTools = request("openai", null);
    withTools.setTools(Collections.singletonList(new ChatRequestTool("function", null)));
    try { UniChatClient.webSearch(client(OPENAI, 200), withTools); fail("custom tools must not be discarded"); }
    catch (IllegalArgumentException expected) { assertTrue(expected.getMessage().contains("custom tools")); }
    assertEquals(0, calls.get());
  }

  @Test
  public void malformedAndHttpFailuresStaySanitizedAndAreNotRetried() throws Exception {
    for (String fixture : Arrays.asList("private-provider-body", "{}", "null")) {
      try { UniChatClient.webSearch(client(fixture, 200), request("openai", null)); fail("invalid response"); }
      catch (IOException expected) { assertFalse(expected.getMessage().contains(fixture)); }
    }
    try { UniChatClient.webSearch(client("private-error-body", 429), request("deepseek", null)); fail("HTTP error"); }
    catch (IOException expected) { assertEquals("Chat provider HTTP status 429", expected.getMessage()); }
    assertEquals(4, calls.get());
  }

  @Test
  public void searchFlagDoesNotSilentlyFallBackToOrdinaryStreaming() {
    UniChatRequest request = request("deepseek", null).setEnable_search(true);
    try { UniChatClient.stream(request, new EventSourceListener() {}); fail("search requires a native stream"); }
    catch (IllegalArgumentException expected) { assertTrue(expected.getMessage().contains("synchronous")); }
    assertNull(request.getStream());
  }
}
