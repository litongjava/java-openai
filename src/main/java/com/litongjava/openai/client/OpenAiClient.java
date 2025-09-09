package com.litongjava.openai.client;

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
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.consts.OpenAiConstants;
import com.litongjava.openai.consts.OpenAiModels;
import com.litongjava.openai.embedding.EmbeddingRequestVo;
import com.litongjava.openai.embedding.EmbeddingResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.StrUtil;
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
public class OpenAiClient {
  public static boolean debug = false;
  public static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConstants.API_PERFIX_URL);

  /**
   * 
   * @param apiKey
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(String apiKey, String bodyString) {
    Map<String, String> header = new HashMap<>();
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    header.put("Authorization", "Bearer " + apiKey);
    return chatCompletions(header, bodyString);
  }

  /**
   * 
   * @param header
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(Map<String, String> header, String bodyString) {
    return chatCompletions(OPENAI_API_URL, header, bodyString);
  }

  /**
   * 
   * @param bodyString
   * @return
   */
  public static Response chatCompletions(String bodyString) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
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
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    header.put("Authorization", "Bearer " + apiKey);
    return chatCompletions(header, bodyString, callback);
  }

  public static EventSource chatCompletions(String bodyString, EventSourceListener listener) {
    Map<String, String> header = new HashMap<>(1);
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }

    header.put("Authorization", "Bearer " + apiKey);
    return chatCompletions(header, bodyString, listener);
  }

  /**
   * 
   * @param header
   * @param bodyString
   * @param callback
   * @return
   */
  public static Call chatCompletions(Map<String, String> header, String bodyString, Callback callback) {
    String apiPerfixUrl = EnvUtils.get("OPENAI_API_URL", OpenAiConstants.API_PERFIX_URL);
    return chatCompletions(apiPerfixUrl, header, bodyString, callback);
  }

  public static EventSource chatCompletions(Map<String, String> header, String bodyString,
      EventSourceListener listener) {
    String apiPerfixUrl = EnvUtils.get("OPENAI_API_URL", OpenAiConstants.API_PERFIX_URL);
    return chatCompletions(apiPerfixUrl, header, bodyString, listener);
  }

  /**
   * 
   * @param model
   * @param chatMessage
   * @return
   */
  public static OpenAiChatResponseVo chatCompletions(String model, OpenAiChatMessage chatMessage) {
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(chatMessage);

    return chatCompletions(model, messages);
  }

  public static OpenAiChatResponseVo chatCompletions(String model, List<OpenAiChatMessage> messages) {
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(model);
    chatRequestVo.setStream(false);
    chatRequestVo.setMessages(messages);
    return chatCompletions(chatRequestVo);
  }

  public static OpenAiChatResponseVo chatCompletions(String model, String systemPrompt,
      List<OpenAiChatMessage> messages) {
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
  public static OpenAiChatResponseVo chatCompletions(String apiKey, OpenAiChatRequestVo chatRequestVo) {
    String json = Json.getSkipNullJson().toJson(chatRequestVo);
    OpenAiChatResponseVo respVo = null;
    try (Response response = chatCompletions(apiKey, json)) {
      String bodyString = response.body().string();
      int code = response.code();
      if (response.isSuccessful()) {
        try {
          respVo = JsonUtils.parse(bodyString, OpenAiChatResponseVo.class);
          respVo.setRawResponse(bodyString);
        } catch (Exception e) {
          throw new GenerateException(ModelPlatformName.OPENAI, "LLM generated failed", OPENAI_API_URL, json, code,
              bodyString);
        }

      } else {
        throw new GenerateException(ModelPlatformName.OPENAI, "LLM generated failed", OPENAI_API_URL, json, code,
            bodyString);
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
  public static OpenAiChatResponseVo chatCompletions(OpenAiChatRequestVo chatRequestVo) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
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

  public static EventSource chatCompletions(OpenAiChatRequestVo chatRequestVo, EventSourceListener listener) {
    String json = Json.getSkipNullJson().toJson(chatRequestVo);
    return chatCompletions(json, listener);
  }

  /**
   * 
   * @param apiPerfixUrl
   * @param apiKey
   * @param chatRequestVo
   * @return
   */
  public static OpenAiChatResponseVo chatCompletions(String apiPerfixUrl, String apiKey,
      OpenAiChatRequestVo chatRequestVo) {
    String json = Json.getSkipNullJson().toJson(chatRequestVo);
    if (debug) {
      log.info("request json:{}", json);
    }
    OpenAiChatResponseVo respVo = null;
    try (Response response = chatCompletions(apiPerfixUrl, apiKey, json)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        try {
          respVo = JsonUtils.parse(bodyString, OpenAiChatResponseVo.class);
          respVo.setRawResponse(bodyString);

        } catch (Exception e) {
          log.error("status code:{},response body:{}", code, bodyString);
          throw new GenerateException(ModelPlatformName.OPENAI, "LLM generated failed", apiPerfixUrl, json, code,
              bodyString);
        }
        respVo.setRawResponse(bodyString);
      } else {
        log.error("status code:{},response body:{}", code, bodyString);
        throw new GenerateException(ModelPlatformName.OPENAI, "LLM generated failed", apiPerfixUrl, json, code,
            bodyString);
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
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    Map<String, String> header = new HashMap<>(1);
    header.put("Authorization", "Bearer " + apiKey);
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
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    header.put("Authorization", "Bearer " + apiKey);
    return chatCompletions(apiPerfixUrl, header, bodyString, callback);
  }

  public static EventSource chatCompletions(String apiPerfixUrl, String apiKey, String bodyString,
      EventSourceListener listener) {
    Map<String, String> header = new HashMap<>(1);
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    header.put("Authorization", "Bearer " + apiKey);
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

    String url = uri + "/chat/completions";
    Request request = new Request.Builder() //
        .url(url) //
        .method("POST", body).headers(headers) //
        .build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * 
   * @param serverUrl
   * @param apiKey
   * @param chatRequestVo
   * @param callback
   * @return
   */
  public static Call chatCompletions(String serverUrl, String apiKey, OpenAiChatRequestVo chatRequestVo,
      Callback callback) {
    return chatCompletions(serverUrl, apiKey, Json.getSkipNullJson().toJson(chatRequestVo), callback);
  }

  public static EventSource chatCompletions(String serverUrl, String apiKey, OpenAiChatRequestVo chatRequestVo,
      EventSourceListener listener) {
    return chatCompletions(serverUrl, apiKey, Json.getSkipNullJson().toJson(chatRequestVo), listener);
  }

  /**
   * 
   * @param apiPrefixUrl
   * @param requestHeaders
   * @param bodyString
   * @param callback
   * @return
   */
  public static Call chatCompletions(String apiPrefixUrl, Map<String, String> requestHeaders, String bodyString,
      Callback callback) {
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    if (debug) {
      log.info(bodyString);
    }
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Headers headers = Headers.of(requestHeaders);

    String url = apiPrefixUrl + "/chat/completions";
    Request request = new Request.Builder() //
        .url(url) //
        .method("POST", body).headers(headers) //
        .build();
    Call newCall = httpClient.newCall(request);
    newCall.enqueue(callback);
    return newCall;
  }

  public static EventSource chatCompletions(String apiPrefixUrl, Map<String, String> requestHeaders, String bodyString,
      EventSourceListener listener) {
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    if (debug) {
      log.info(bodyString);
    }
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Headers headers = Headers.of(requestHeaders);

    String url = apiPrefixUrl + "/chat/completions";
    Request request = new Request.Builder() //
        .url(url) //
        .method("POST", body).headers(headers) //
        .build();

    // 这里就发起 SSE 请求了
    EventSource source = EventSources.createFactory(httpClient).newEventSource(request, listener);
    return source;
  }

  /**
   * 
   * @param role
   * @param prompt
   * @return
   */
  public static OpenAiChatResponseVo chat(String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage("user", prompt);
    return chatCompletions(OpenAiModels.GPT_4O_MINI, chatMessage);
  }

  /**
   * 
   * @param role
   * @param prompt
   * @return
   */
  public static OpenAiChatResponseVo chatWithRole(String role, String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage(role, prompt);
    return chatCompletions(OpenAiModels.GPT_4O_MINI, chatMessage);
  }

  /**
   * 
   * @param model
   * @param role
   * @param prompt
   * @return
   */
  public static OpenAiChatResponseVo chatWithModel(String model, String role, String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage(role, prompt);
    return chatCompletions(model, chatMessage);
  }

  public static OpenAiChatResponseVo chatWithModel(String apiUrl, String key, String model, String role,
      String prompt) {
    OpenAiChatMessage chatMessage = new OpenAiChatMessage(role, prompt);
    return chatCompletions(apiUrl, key, model, chatMessage);
  }

  public static OpenAiChatResponseVo chatCompletions(String apiUrl, String key, String model,
      OpenAiChatMessage chatMessage) {
    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(chatMessage);
    return chatCompletions(apiUrl, key, model, messages);
  }

  public static OpenAiChatResponseVo chatCompletions(String apiUrl, String key, String model,
      List<OpenAiChatMessage> messages) {
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(model);
    chatRequestVo.setStream(false);
    chatRequestVo.setMessages(messages);
    return chatCompletions(apiUrl, key, chatRequestVo);
  }

  public static Response embeddings(String bodyString) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return embeddings(apiKey, bodyString);
  }

  public static Response embeddings(String apiKey, String bodyString) {
    String serverUrl = EnvUtils.get("OPENAI_API_URL");
    return embeddings(serverUrl, apiKey, bodyString);
  }

  public static Response embeddings(String api_perfix_url, String apiKey, String bodyString) {
    if (api_perfix_url == null) {
      api_perfix_url = OpenAiConstants.API_PERFIX_URL;
    }

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Map<String, String> requestHeaders = new HashMap<>(1);
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    requestHeaders.put("Authorization", "Bearer " + apiKey);

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
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return embeddings(apiKey, embeddingRequestVo).getData().get(0).getEmbedding();
  }

  public static float[] embeddingArray(String apiKey, String input, String model) {
    EmbeddingRequestVo embeddingRequestVo = new EmbeddingRequestVo(input, model);
    return embeddings(apiKey, embeddingRequestVo).getData().get(0).getEmbedding();
  }

  public static float[] embeddingArray(String input) {
    return embeddingArray(input, OpenAiModels.TEXT_EMBEDDING_3_SMALL);
  }

  public static float[] embeddingArrayByLargeModel(String input) {
    return embeddingArray(input, OpenAiModels.TEXT_EMBEDDING_3_LARGE);
  }

  public static EmbeddingResponseVo embeddings(EmbeddingRequestVo embeddingRequestVo) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return embeddings(apiKey, embeddingRequestVo);
  }

  public static EmbeddingResponseVo embeddings(String serverUrl, String apiKey, EmbeddingRequestVo reoVo) {
    EmbeddingResponseVo respVo = null;
    try (Response response = embeddings(serverUrl, apiKey, Json.getSkipNullJson().toJson(reoVo))) {
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, EmbeddingResponseVo.class);
      } else {
        throw new RuntimeException("status:" + response.code() + ",sbody:" + bodyString);
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
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, EmbeddingResponseVo.class);
      } else {
        String serverUrl = EnvUtils.get("OPENAI_API_URL");
        throw new GenerateException(ModelPlatformName.OPENAI, "Failed to Embedding", serverUrl, json, response.code(),
            bodyString);
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

  public static OpenAiChatResponseVo chatWithImage(String prompt, byte[] bytes, String suffix) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return chatWithImage(apiKey, prompt, bytes, suffix);
  }

  public static OpenAiChatResponseVo chatWithImage(String apiKey, String model, String prompt, byte[] bytes,
      String suffix) {

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

  public static OpenAiChatResponseVo chatWithImage(String apiKey, String prompt, byte[] bytes, String suffix) {
    return chatWithImage(apiKey, OpenAiModels.GPT_4O_MINI, prompt, bytes, suffix);
  }

}