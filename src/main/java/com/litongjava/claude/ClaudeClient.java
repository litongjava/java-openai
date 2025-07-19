package com.litongjava.claude;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.litongjava.consts.ModelPlatformName;
import com.litongjava.exception.GenerateException;
import com.litongjava.openai.chat.ChatMessageContent;
import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.embedding.EmbeddingRequestVo;
import com.litongjava.openai.embedding.EmbeddingResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.Json;
import com.litongjava.tio.utils.json.JsonUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

@Slf4j
public class ClaudeClient {
  public static boolean debug = false;
  public static String CLAUDE_API_URL = EnvUtils.get("CLAUDE_API_URL", ClaudeConsts.API_PERFIX_URL);

  /**
   * 
   * @param apiKey
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(String apiKey, String bodyString) {
    Map<String, String> header = new HashMap<>();
    header.put("x-api-key", apiKey);
    header.put("anthropic-version", "2023-06-01");
    return chatCompletions(header, bodyString);
  }

  /**
   * @param header
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(Map<String, String> header, String bodyString) {
    return chatCompletions(CLAUDE_API_URL, header, bodyString);
  }

  /**
   * 
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(String bodyString) {
    String apiKey = EnvUtils.get("CLAUDE_API_KEY");
    return chatCompletions(apiKey, bodyString);
  }

  /**
   * 
   * @param bodyString
   * @param callback
   * @return
   */
  public static Call chatCompletions(String bodyString, Callback callback) {
    Map<String, String> header = new HashMap<>(1);
    String apiKey = EnvUtils.get("CLAUDE_API_KEY");
    header.put("x-api-key", apiKey);
    header.put("anthropic-version", "2023-06-01");
    return chatCompletions(header, bodyString, callback);
  }

  /**
   * 
   * @param header
   * @param bodyString
   * @param callback
   * @return
   */
  public static Call chatCompletions(Map<String, String> header, String bodyString, Callback callback) {
    String apiPerfixUrl = EnvUtils.get("CLAUDE_API_URL", ClaudeConsts.API_PERFIX_URL);
    return chatCompletions(apiPerfixUrl, header, bodyString, callback);
  }

  /**
   * 
   * @param model
   * @param chatMessage
   * @return
   */
  public static ClaudeChatResponseVo chatCompletions(String model, OpenAiChatMessage chatMessage) {
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(chatMessage);

    return chatCompletions(model, messages);
  }

  public static ClaudeChatResponseVo chatCompletions(String model, List<OpenAiChatMessage> messages) {
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(model);
    chatRequestVo.setStream(false);
    chatRequestVo.setMessages(messages);
    chatRequestVo.setMax_tokens(64000);
    return chatCompletions(chatRequestVo);
  }

  public static ClaudeChatResponseVo chatCompletions(String model, String systemPrompt, List<OpenAiChatMessage> messages) {
    messages.add(0, OpenAiChatMessage.buildSystem(systemPrompt));
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(model);
    chatRequestVo.setStream(false);
    chatRequestVo.setMessages(messages);
    return chatCompletions(chatRequestVo);
  }

  /**
   * 
   * @param apiKey
   * @param chatRequestVo
   * @return
   */
  public static ClaudeChatResponseVo chatCompletions(String apiKey, OpenAiChatRequestVo chatRequestVo) {
    if (chatRequestVo.getMax_tokens() == null) {
      chatRequestVo.setMax_tokens(64000);
    }
    String json = Json.getSkipNullJson().toJson(chatRequestVo);
    if (debug) {
      System.out.println(json);
    }
    ClaudeChatResponseVo respVo = null;
    try (Response response = chatCompletions(apiKey, json)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, ClaudeChatResponseVo.class);
      } else {
        String apiPerfixUrl = EnvUtils.get("CLAUDE_API_URL", ClaudeConsts.API_PERFIX_URL);
        throw new GenerateException(ModelPlatformName.ANTHROPIC, "Claude generateContent failed", apiPerfixUrl, json, code, bodyString);
      }
    } catch (IOException e) {
      log.error(e.getMessage() + " request json:" + json);
      throw new RuntimeException(e.getMessage(), e);
    }
    return respVo;
  }

  /**
   * 
   * @param chatRequestVo
   * @return
   */
  public static ClaudeChatResponseVo chatCompletions(OpenAiChatRequestVo chatRequestVo) {
    String apiKey = EnvUtils.get("CLAUDE_API_KEY");
    return chatCompletions(apiKey, chatRequestVo);
  }

  /**
   * 
   * @param chatRequestVo
   * @param callback
   * @return
   */
  public static Call chatCompletions(OpenAiChatRequestVo chatRequestVo, Callback callback) {
    String json = Json.getSkipNullJson().toJson(chatRequestVo);
    return chatCompletions(json, callback);
  }

  /**
   * 
   * @param apiPerfixUrl
   * @param apiKey
   * @param chatRequestVo
   * @return
   */
  public static ClaudeChatResponseVo chatCompletions(String apiPerfixUrl, String apiKey, OpenAiChatRequestVo chatRequestVo) {
    Integer max_tokens = chatRequestVo.getMax_tokens();
    if (max_tokens == null) {
      chatRequestVo.setMax_tokens(64000);
    }
    String json = Json.getSkipNullJson().toJson(chatRequestVo);
    if (debug) {
      log.info(apiKey + ":" + json);
    }
    ClaudeChatResponseVo respVo = null;
    try (Response response = chatCompletions(apiPerfixUrl, apiKey, json)) {
      String bodyString = response.body().string();
      int code = response.code();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, ClaudeChatResponseVo.class);
      } else {
        throw new GenerateException(ModelPlatformName.ANTHROPIC, "Claude generateContent failed", apiPerfixUrl, json, code, bodyString);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return respVo;
  }

  /**
   * 
   * @param apiPerfixUrl
   * @param apiKey
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(String apiPerfixUrl, String apiKey, String bodyString) {
    Map<String, String> header = new HashMap<>(1);
    header.put("x-api-key", apiKey);
    header.put("anthropic-version", "2023-06-01");
    return chatCompletions(apiPerfixUrl, header, bodyString);
  }

  /**
   * 
   * @param apiPerfixUrl
   * @param apiKey
   * @param bodyString
   * @param callback
   * @return
   */
  public static Call chatCompletions(String apiPerfixUrl, String apiKey, String bodyString, Callback callback) {
    Map<String, String> header = new HashMap<>(1);
    header.put("x-api-key", apiKey);
    header.put("anthropic-version", "2023-06-01");
    return chatCompletions(apiPerfixUrl, header, bodyString, callback);
  }

  public static EventSource chatCompletions(String apiPerfixUrl, String apiKey, OpenAiChatRequestVo chatRequestVo, EventSourceListener listener) {
    String bodyString = JsonUtils.toSkipNullJson(chatRequestVo);
    return chatCompletions(apiPerfixUrl, apiKey, bodyString, listener);
  }

  public static EventSource chatCompletions(String apiPerfixUrl, String apiKey, String bodyString, EventSourceListener listener) {
    Map<String, String> header = new HashMap<>(1);
    header.put("x-api-key", apiKey);
    header.put("anthropic-version", "2023-06-01");
    return chatCompletions(apiPerfixUrl, header, bodyString, listener);
  }

  /**
   * 
   * @param uri
   * @param requestHeaders
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(String uri, Map<String, String> requestHeaders, String bodyString) {

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Headers headers = Headers.of(requestHeaders);

    String url = uri + "/messages";
    Request request = new Request.Builder().url(url).method("POST", body).headers(headers).build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static Call chatCompletions(String apiKey, OpenAiChatRequestVo chatRequestVo, Callback callback) {
    String apiPerfixUrl = EnvUtils.get("CLAUDE_API_URL", ClaudeConsts.API_PERFIX_URL);
    return chatCompletions(apiPerfixUrl, apiKey, Json.getSkipNullJson().toJson(chatRequestVo), callback);
  }

  public static EventSource chatCompletions(String apiKey, OpenAiChatRequestVo chatRequestVo, EventSourceListener listener) {
    String apiPerfixUrl = EnvUtils.get("CLAUDE_API_URL", ClaudeConsts.API_PERFIX_URL);
    return chatCompletions(apiPerfixUrl, apiKey, chatRequestVo, listener);
  }

  /**
   * 
   * @param serverUrl
   * @param apiKey
   * @param chatRequestVo
   * @param callback
   * @return
   */
  public static Call chatCompletions(String serverUrl, String apiKey, OpenAiChatRequestVo chatRequestVo, Callback callback) {
    return chatCompletions(serverUrl, apiKey, Json.getSkipNullJson().toJson(chatRequestVo), callback);
  }

  /**
   * 
   * @param apiPrefixUrl
   * @param requestHeaders
   * @param bodyString
   * @param callback
   * @return
   */
  public static Call chatCompletions(String apiPrefixUrl, Map<String, String> requestHeaders, String bodyString, Callback callback) {
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    if (debug) {
      log.info(bodyString);
    }
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Headers headers = Headers.of(requestHeaders);

    String url = apiPrefixUrl + "/messages";
    Request request = new Request.Builder() //
        .url(url) //
        .method("POST", body).headers(headers) //
        .build();
    Call newCall = httpClient.newCall(request);
    newCall.enqueue(callback);
    return newCall;
  }

  public static EventSource chatCompletions(String apiPrefixUrl, Map<String, String> requestHeaders, String bodyString, EventSourceListener listener) {
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    if (debug) {
      log.info(bodyString);
    }
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Headers headers = Headers.of(requestHeaders);

    String url = apiPrefixUrl + "/messages";
    Request request = new Request.Builder() //
        .url(url) //
        .method("POST", body).headers(headers) //
        .build();
    return EventSources.createFactory(httpClient).newEventSource(request, listener);
  }

  /**
   * 
   * @param role
   * @param prompt
   * @return
   */
  public static ClaudeChatResponseVo chat(String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage("user", prompt);
    return chatCompletions(ClaudeModels.CLAUDE_3_7_SONNET_20250219, chatMessage);
  }

  /**
   * 
   * @param role
   * @param prompt
   * @return
   */
  public static ClaudeChatResponseVo chatWithRole(String role, String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage(role, prompt);
    return chatCompletions(ClaudeModels.CLAUDE_3_7_SONNET_20250219, chatMessage);
  }

  /**
   * 
   * @param model
   * @param role
   * @param prompt
   * @return
   */
  public static ClaudeChatResponseVo chatWithModel(String model, String role, String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage(role, prompt);
    return chatCompletions(model, chatMessage);
  }

  public static ClaudeChatResponseVo chatWithModel(String key, String model, String role, String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage(role, prompt);
    return chatCompletions(CLAUDE_API_URL, key, model, chatMessage);
  }

  public static ClaudeChatResponseVo chatWithModel(String apiUrl, String key, String model, String role, String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage(role, prompt);
    return chatCompletions(apiUrl, key, model, chatMessage);
  }

  public static ClaudeChatResponseVo chatCompletions(String apiUrl, String key, String model, OpenAiChatMessage chatMessage) {
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(chatMessage);
    return chatCompletions(apiUrl, key, model, messages);
  }

  public static ClaudeChatResponseVo chatCompletions(String apiUrl, String key, String model, List<OpenAiChatMessage> messages) {
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(model);
    chatRequestVo.setStream(false);
    chatRequestVo.setMessages(messages);
    return chatCompletions(apiUrl, key, chatRequestVo);
  }

  public static Response embeddings(String bodyString) {
    String apiKey = EnvUtils.get("CLAUDE_API_KEY");
    return embeddings(apiKey, bodyString);
  }

  public static Response embeddings(String apiKey, String bodyString) {
    String serverUrl = EnvUtils.get("CLAUDE_API_URL");
    return embeddings(serverUrl, apiKey, bodyString);
  }

  public static Response embeddings(String api_perfix_url, String apiKey, String bodyString) {
    if (api_perfix_url == null) {
      api_perfix_url = ClaudeConsts.API_PERFIX_URL;
    }

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Map<String, String> requestHeaders = new HashMap<>(1);
    requestHeaders.put("x-api-key", apiKey);
    requestHeaders.put("anthropic-version", "2023-06-01");

    Headers headers = Headers.of(requestHeaders);

    Request request = new Request.Builder() //
        .url(api_perfix_url + "/embeddings") //
        .method("POST", body).headers(headers) //
        .build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static float[] embeddingArray(String input, String model) {
    EmbeddingRequestVo embeddingRequestVo = new EmbeddingRequestVo(model, input);
    String apiKey = EnvUtils.get("CLAUDE_API_KEY");
    return embeddings(apiKey, embeddingRequestVo).getData().get(0).getEmbedding();
  }

  public static float[] embeddingArray(String serverUrl, String input, String model) {
    EmbeddingRequestVo embeddingRequestVo = new EmbeddingRequestVo(input, model);
    return embeddings(serverUrl, embeddingRequestVo).getData().get(0).getEmbedding();
  }

  public static EmbeddingResponseVo embeddings(EmbeddingRequestVo embeddingRequestVo) {
    String apiKey = EnvUtils.get("CLAUDE_API_KEY");
    return embeddings(apiKey, embeddingRequestVo);
  }

  public static EmbeddingResponseVo embeddings(String serverUrl, String apiKey, EmbeddingRequestVo reoVo) {
    EmbeddingResponseVo respVo = null;
    String json = Json.getSkipNullJson().toJson(reoVo);
    try (Response response = embeddings(serverUrl, apiKey, json)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, EmbeddingResponseVo.class);
      } else {
        throw new GenerateException(ModelPlatformName.ANTHROPIC, "Claude generateContent failed", serverUrl, json, code, bodyString);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return respVo;
  }

  public static EmbeddingResponseVo embeddings(String apiKey, EmbeddingRequestVo reoVo) {
    EmbeddingResponseVo respVo = null;
    String json = Json.getSkipNullJson().toJson(reoVo);
    try (Response response = embeddings(apiKey, json)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, EmbeddingResponseVo.class);
      } else {
        throw new GenerateException(ModelPlatformName.ANTHROPIC, "Claude generateContent failed", ClaudeConsts.API_PERFIX_URL, json, code, bodyString);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return respVo;
  }

  public static float[] embeddingArray(String serverUrl, String apiKey, String input, String model) {
    EmbeddingRequestVo reqVo = new EmbeddingRequestVo(input, model);
    return embeddingArray(serverUrl, apiKey, reqVo);
  }

  public static float[] embeddingArray(String serverUrl, String apiKey, EmbeddingRequestVo reqVo) {
    EmbeddingResponseVo embeddings = embeddings(serverUrl, apiKey, reqVo);
    return embeddings.getData().get(0).getEmbedding();
  }

  public static ClaudeChatResponseVo chatWithImage(String prompt, byte[] bytes, String suffix) {
    String apiKey = EnvUtils.get("CLAUDE_API_KEY");
    return chatWithImage(apiKey, prompt, bytes, suffix);
  }

  public static ClaudeChatResponseVo chatWithImage(String apiKey, String model, String prompt, byte[] bytes, String suffix) {

    ChatMessageContent text = new ChatMessageContent(prompt);
    ChatMessageContent image = new ChatMessageContent(bytes, suffix);

    List<ChatMessageContent> multiContents = new ArrayList<>();
    multiContents.add(text);
    multiContents.add(image);

    OpenAiChatMessage user = new OpenAiChatMessage();
    user.role("user").multiContents(multiContents);

    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(user);

    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setModel(model);
    openAiChatRequestVo.setMessages(messages);
    return chatCompletions(apiKey, openAiChatRequestVo);
  }

  public static ClaudeChatResponseVo chatWithImage(String apiKey, String prompt, byte[] bytes, String suffix) {
    return chatWithImage(apiKey, ClaudeModels.CLAUDE_3_7_SONNET_20250219, prompt, bytes, suffix);
  }

}