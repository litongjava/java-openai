package nexus.io.search;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import nexus.io.chat.ChatRequestUtils;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.claude.ClaudeChatResponse;
import nexus.io.claude.ClaudeClient;
import nexus.io.claude.ClaudeMessageContent;
import nexus.io.claude.ClaudeWebSearchTool;
import nexus.io.consts.ModelPlatformName;
import nexus.io.deepseek.DeepSeekClient;
import nexus.io.exception.GenerateException;
import nexus.io.openai.chat.ChatRequestTool;
import nexus.io.openai.chat.ChatRequestToolCallFunction;
import nexus.io.openai.chat.OpenAiChatMessage;
import nexus.io.openai.chat.OpenAiChatRequest;
import nexus.io.openai.responses.OpenAiResponsesClient;
import nexus.io.openai.responses.OpenAiResponsesOutput;
import nexus.io.openai.responses.OpenAiResponsesOutputContent;
import nexus.io.openai.responses.OpenAiResponsesRequest;
import nexus.io.openai.responses.OpenAiResponsesResponse;
import nexus.io.openai.responses.OpenAiWebSearchTool;
import nexus.io.tio.utils.json.JsonUtils;
import nexus.io.tio.utils.environment.EnvUtils;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

/** Local HTTP contract tests: no credentials or external API calls are required. */
public class WebSearchClientTest {
  private static final String OPENAI_RESPONSE = "{\"id\":\"resp_1\",\"status\":\"completed\",\"output\":["
      + "{\"type\":\"web_search_call\",\"id\":\"ws_1\",\"status\":\"completed\",\"action\":{"
      + "\"type\":\"search\",\"queries\":[\"Java release\"],\"sources\":[{\"type\":\"url\",\"url\":\"https://example.com/java\"}]}},"
      + "{\"type\":\"message\",\"role\":\"assistant\",\"content\":[{\"type\":\"output_text\",\"text\":\"Latest Java\","
      + "\"annotations\":[{\"type\":\"url_citation\",\"url\":\"https://example.com/java\",\"title\":\"Java\","
      + "\"start_index\":0,\"end_index\":11}]}]}]}";

  private static final String MESSAGES_RESPONSE = "{\"id\":\"msg_1\",\"stop_reason\":\"end_turn\",\"content\":["
      + "{\"type\":\"text\",\"text\":\"Found: \"},"
      + "{\"type\":\"server_tool_use\",\"id\":\"srv_1\",\"name\":\"web_search\",\"input\":{\"query\":\"Java release\"}},"
      + "{\"type\":\"web_search_tool_result\",\"tool_use_id\":\"srv_1\",\"content\":[{\"type\":\"web_search_result\","
      + "\"url\":\"https://example.com/java\",\"title\":\"Java\",\"page_age\":\"2026-10-12\",\"encrypted_content\":\"opaque-result\"}]},"
      + "{\"type\":\"text\",\"text\":\"Latest Java\",\"citations\":[{\"type\":\"web_search_result_location\","
      + "\"url\":\"https://example.com/java\",\"title\":\"Java\",\"encrypted_index\":\"opaque-index\",\"cited_text\":\"Release notes\"}]}],"
      + "\"usage\":{\"input_tokens\":50,\"output_tokens\":20,\"server_tool_use\":{\"web_search_requests\":1}}}";

  private HttpServer server;
  private String baseUrl;
  private volatile String responseBody;
  private volatile int status = 200;
  private volatile String contentType = "application/json";
  private final AtomicReference<JSONObject> requestBody = new AtomicReference<>();
  private final AtomicReference<String> requestPath = new AtomicReference<>();
  private final AtomicReference<Headers> requestHeaders = new AtomicReference<>();
  private final AtomicReference<String> requestMethod = new AtomicReference<>();

  @Before
  public void startServer() throws Exception {
    responseBody = MESSAGES_RESPONSE;
    server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    server.createContext("/", exchange -> {
      try (InputStream input = exchange.getRequestBody(); ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
        byte[] buffer = new byte[4096];
        int read;
        while ((read = input.read(buffer)) != -1) {
          bytes.write(buffer, 0, read);
        }
        requestBody.set(JSON.parseObject(new String(bytes.toByteArray(), StandardCharsets.UTF_8)));
        requestPath.set(exchange.getRequestURI().getPath());
        requestHeaders.set(exchange.getRequestHeaders());
        requestMethod.set(exchange.getRequestMethod());
        byte[] result = responseBody.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, result.length);
        exchange.getResponseBody().write(result);
      } finally {
        exchange.close();
      }
    });
    server.start();
    baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
  }

  @After
  public void stopServer() {
    if (server != null) {
      server.stop(0);
    }
  }

  @Test
  public void openAiSendsResponsesToolAndParsesSourcesAndCitations() {
    responseBody = OPENAI_RESPONSE;
    OpenAiResponsesResponse result = OpenAiResponsesClient.webSearch(baseUrl + "/v1", "test-key", "gpt-5.4", "Java release");
    assertEquals("/v1/responses", requestPath.get());
    assertEquals("POST", requestMethod.get());
    assertEquals("Bearer test-key", requestHeaders.get().getFirst("Authorization"));
    JSONObject body = requestBody.get();
    assertEquals("web_search", body.getJSONArray("tools").getJSONObject(0).getString("type"));
    assertEquals("required", body.getString("tool_choice"));
    assertEquals("web_search_call.action.sources", body.getJSONArray("include").getString(0));
    assertEquals("Java release", body.getJSONArray("input").getJSONObject(0).getJSONArray("content").getJSONObject(0).getString("text"));
    assertEquals("Latest Java", result.getOutputText());
    assertEquals("Java release", result.getOutput().get(0).getAction().getQueries().get(0));
    assertEquals("https://example.com/java", result.getOutput().get(0).getAction().getSources().get(0).getUrl());
    OpenAiResponsesOutputContent text = result.getOutput().get(1).getContent().get(0);
    assertEquals(Integer.valueOf(11), text.getAnnotations().get(0).getEnd_index());
    assertEquals("Java", text.getAnnotations().get(0).getTitle());
    assertEquals(OPENAI_RESPONSE, result.getRawResponse());
  }

  @Test
  public void claudeSendsMessagesSearchToolAndPreservesAllSearchBlocks() {
    ClaudeChatResponse result = ClaudeClient.webSearch(baseUrl + "/v1", "test-key", "claude-sonnet-4-6", "Java release");
    assertEquals("/v1/messages", requestPath.get());
    assertMessagesSearchRequest();
    assertSearchResponse(result);
  }

  @Test
  public void unifiedDefaultClientConvenienceAndGenerateUseTheSearchRoutes() {
    String oldUrl = EnvUtils.get("DEEPSEEK_SEARCH_BASE_URL");
    String oldKey = EnvUtils.get("DEEPSEEK_API_KEY");
    try {
      EnvUtils.set("DEEPSEEK_SEARCH_BASE_URL", baseUrl + "/anthropic/v1");
      EnvUtils.set("DEEPSEEK_API_KEY", "test-key");
      UniChatResponse result = UniChatClient.webSearch("deepseek-v4-flash", "Java release");
      assertEquals("Found: Latest Java", result.getMessage().getContent());
      assertEquals("/anthropic/v1/messages", requestPath.get());
      assertMessagesSearchRequest();
      result = UniChatClient.webSearch("deepseek", null, "Java release");
      assertEquals("Found: Latest Java", result.getMessage().getContent());
      UniChatRequest request = new UniChatRequest().setModel("deepseek-v4-flash")
          .setUserPrompts("Java release").setEnable_search(true);
      result = UniChatClient.generate("override-key", request);
      assertEquals("override-key", requestHeaders.get().getFirst("x-api-key"));
      assertEquals("Found: Latest Java", result.getMessage().getContent());
      assertEquals(Collections.singletonList("https://example.com/java"), result.getCitations());
    } finally {
      EnvUtils.set("DEEPSEEK_SEARCH_BASE_URL", oldUrl);
      EnvUtils.set("DEEPSEEK_API_KEY", oldKey);
    }
  }

  @Test
  public void deepSeekUsesAnthropicRouteAndBothAuthenticationHeaders() {
    ClaudeChatResponse result = DeepSeekClient.webSearch(baseUrl + "/anthropic/v1", "test-key", "deepseek-v4-flash", "Java release");
    assertEquals("/anthropic/v1/messages", requestPath.get());
    assertEquals("Bearer test-key", requestHeaders.get().getFirst("Authorization"));
    assertEquals("deepseek-v4-flash", requestBody.get().getString("model"));
    assertMessagesSearchRequest();
    assertSearchResponse(result);
  }

  private void assertMessagesSearchRequest() {
    assertEquals("test-key", requestHeaders.get().getFirst("x-api-key"));
    assertEquals("2023-06-01", requestHeaders.get().getFirst("anthropic-version"));
    JSONObject body = requestBody.get();
    assertEquals(4096, body.getIntValue("max_tokens"));
    JSONObject tool = body.getJSONArray("tools").getJSONObject(0);
    assertEquals("web_search_20250305", tool.getString("type"));
    assertEquals("web_search", tool.getString("name"));
    assertEquals(5, tool.getIntValue("max_uses"));
    assertFalse(tool.containsKey("function"));
    assertFalse(body.containsKey("web_search_options"));
  }

  private void assertSearchResponse(ClaudeChatResponse result) {
    assertEquals("Found: Latest Java", result.getOutputText());
    assertEquals("Java release", result.getContent().get(1).getInput().get("query"));
    ClaudeMessageContent block = result.getContent().get(2);
    assertEquals("srv_1", block.getTool_use_id());
    assertEquals("https://example.com/java", block.webSearchResults().get(0).getUrl());
    assertEquals("opaque-result", block.webSearchResults().get(0).getEncrypted_content());
    assertNull(block.webSearchError());
    assertEquals("opaque-index", result.getContent().get(3).getCitations().get(0).getEncrypted_index());
    assertEquals(Integer.valueOf(1), result.getUsage().getServer_tool_use().get("web_search_requests"));
    assertEquals(MESSAGES_RESPONSE, result.getRawResponse());
  }

  @Test
  public void http200SearchErrorsRemainDistinctFromEmptyResults() {
    responseBody = "{\"content\":[{\"type\":\"web_search_tool_result\",\"content\":{"
        + "\"type\":\"web_search_tool_result_error\",\"error_code\":\"max_uses_exceeded\"}},"
        + "{\"type\":\"web_search_tool_result\",\"content\":[]}]}";
    ClaudeChatResponse result = DeepSeekClient.webSearch(baseUrl, "test-key", "deepseek-v4-flash", "query");
    assertEquals("max_uses_exceeded", result.getContent().get(0).webSearchError().getError_code());
    assertTrue(result.getContent().get(0).webSearchResults().isEmpty());
    assertNull(result.getContent().get(1).webSearchError());
    assertTrue(result.getContent().get(1).webSearchResults().isEmpty());
    assertNull(result.getOutputText());
  }

  @Test
  public void pauseTurnContentCanBeReplayedWithoutLosingEncryptedFields() {
    responseBody = MESSAGES_RESPONSE.replace("end_turn", "pause_turn");
    ClaudeChatResponse result = DeepSeekClient.webSearch(baseUrl, "test-key", "deepseek-v4-flash", "query");
    assertEquals("pause_turn", result.getStop_reason());
    OpenAiChatMessage assistant = new OpenAiChatMessage();
    assistant.setRole("assistant");
    assistant.setContent(result.getContent());
    JSONObject replay = JSON.parseObject(ChatRequestUtils.toSkipNullJson(assistant));
    JSONArray blocks = replay.getJSONArray("content");
    assertEquals(JSON.parseObject(responseBody).getJSONArray("content"), blocks);
    assertFalse(blocks.getJSONObject(2).containsKey("webSearchResults"));
    assertFalse(blocks.getJSONObject(2).containsKey("webSearchError"));
  }

  @Test
  public void filtersAndLocationHaveProviderSpecificWireShapes() {
    WebSearchUserLocation location = new WebSearchUserLocation().setCountry("CN").setCity("Shanghai");
    OpenAiWebSearchTool openAiTool = new OpenAiWebSearchTool().setExternal_web_access(false)
        .setSearch_context_size("high").setUser_location(location)
        .setFilters(new OpenAiWebSearchTool.Filters().setAllowed_domains(Collections.singletonList("example.com")));
    OpenAiResponsesRequest request = OpenAiResponsesClient.webSearchRequest("gpt-5.4", "query")
        .setTools(Collections.singletonList(openAiTool));
    JSONObject openAi = JSON.parseObject(ChatRequestUtils.toSkipNullJson(request)).getJSONArray("tools").getJSONObject(0);
    assertFalse(openAi.getBooleanValue("external_web_access"));
    assertEquals("example.com", openAi.getJSONObject("filters").getJSONArray("allowed_domains").getString(0));
    assertEquals("approximate", openAi.getJSONObject("user_location").getString("type"));

    ClaudeWebSearchTool claudeTool = new ClaudeWebSearchTool().setMax_uses(2).setUser_location(location)
        .setBlocked_domains(Collections.singletonList("example.com"));
    OpenAiChatRequest claudeRequest = ClaudeClient.webSearchRequest("claude-sonnet-4-6", "query")
        .setTools(Collections.singletonList(claudeTool));
    JSONObject claude = JSON.parseObject(ChatRequestUtils.toSkipNullJson(claudeRequest)).getJSONArray("tools").getJSONObject(0);
    assertEquals("example.com", claude.getJSONArray("blocked_domains").getString(0));
    assertFalse(claude.containsKey("filters"));
    assertEquals("Shanghai", claude.getJSONObject("user_location").getString("city"));
  }

  @Test
  public void existingConstructorsAndFunctionToolShapeRemainCompatible() {
    ChatRequestToolCallFunction function = new ChatRequestToolCallFunction();
    function.setName("lookup");
    ChatRequestTool tool = new ChatRequestTool("function", function);
    JSONObject json = JSON.parseObject(ChatRequestUtils.toSkipNullJson(tool));
    assertEquals(2, json.size());
    assertEquals("lookup", json.getJSONObject("function").getString("name"));
    OpenAiResponsesRequest request = new OpenAiResponsesRequest("model", null, null, null, null, false, false);
    assertFalse(JSON.parseObject(ChatRequestUtils.toSkipNullJson(request)).containsKey("tools"));
    OpenAiResponsesOutputContent text = new OpenAiResponsesOutputContent("output_text", "Hello");
    OpenAiResponsesOutput output = new OpenAiResponsesOutput("id", "message", "assistant", "completed", Collections.singletonList(text), null);
    assertEquals("Hello", output.getContent().get(0).getText());
    ClaudeChatResponse response = JsonUtils.parse("{\"content\":[{\"type\":\"text\",\"text\":\"Hello\"}]}", ClaudeChatResponse.class);
    assertEquals("Hello", response.getOutputText());
  }

  @Test
  public void httpErrorsKeepPlatformStatusAndRawBody() {
    status = 429;
    responseBody = "{\"error\":{\"message\":\"rate limited\"}}";
    for (String platform : Arrays.asList(ModelPlatformName.OPENAI, ModelPlatformName.ANTHROPIC, ModelPlatformName.DEEPSEEK)) {
      try {
        if (ModelPlatformName.OPENAI.equals(platform)) {
          OpenAiResponsesClient.webSearch(baseUrl, "test-key", "model", "query");
        } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
          ClaudeClient.webSearch(baseUrl, "test-key", "model", "query");
        } else {
          DeepSeekClient.webSearch(baseUrl, "test-key", "model", "query");
        }
        fail("Expected an HTTP error");
      } catch (GenerateException expected) {
        assertEquals(platform, expected.getProvider());
        assertEquals(Integer.valueOf(429), expected.getStatusCode());
        assertEquals(responseBody, expected.getResponseBody());
      }
    }
  }

  @Test
  public void streamingKeepsSearchEventNamesAndPayloadsForAllPlatforms() throws Exception {
    contentType = "text/event-stream";
    for (String platform : Arrays.asList(ModelPlatformName.OPENAI, ModelPlatformName.ANTHROPIC, ModelPlatformName.DEEPSEEK)) {
      String type = ModelPlatformName.OPENAI.equals(platform) ? "response.web_search_call.completed" : "content_block_start";
      String data = "{\"type\":\"" + type + "\",\"marker\":\"citation-payload\"}";
      responseBody = "event: " + type + "\ndata: " + data + "\n\n";
      CountDownLatch closed = new CountDownLatch(1);
      AtomicReference<Throwable> failure = new AtomicReference<>();
      List<String> events = new ArrayList<>();
      EventSourceListener listener = new EventSourceListener() {
        @Override public void onEvent(EventSource source, String id, String eventType, String payload) {
          events.add(eventType + " " + payload);
        }
        @Override public void onClosed(EventSource source) { closed.countDown(); }
        @Override public void onFailure(EventSource source, Throwable error, Response response) {
          failure.set(error == null ? new AssertionError("SSE failed") : error);
          closed.countDown();
        }
      };
      EventSource stream;
      if (ModelPlatformName.OPENAI.equals(platform)) {
        stream = OpenAiResponsesClient.responses(baseUrl, "test-key", OpenAiResponsesClient.webSearchRequest("model", "query"), listener);
      } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
        stream = ClaudeClient.chatCompletions(baseUrl, "test-key", ClaudeClient.webSearchRequest("model", "query"), listener);
      } else {
        stream = DeepSeekClient.messages(baseUrl, "test-key", ClaudeClient.webSearchRequest("model", "query"), listener);
      }
      try {
        assertTrue("SSE stream did not close", closed.await(10, TimeUnit.SECONDS));
        assertNull(failure.get());
        assertEquals(Collections.singletonList(type + " " + data), events);
        assertTrue(requestBody.get().getBooleanValue("stream"));
      } finally {
        stream.cancel();
      }
    }
  }
}
