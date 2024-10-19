package com.litongjava.openai.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.constants.OpenAiConstatns;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.openai.embedding.EmbeddingRequestVo;
import com.litongjava.openai.embedding.EmbeddingResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAiClient {

  public static Response chatCompletions(String apiKey, String bodyString) {
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", "Bearer " + apiKey);

    return chatCompletions(header, bodyString);
  }

  public static Response chatCompletions(Map<String, String> header, String bodyString) {
    String serverUrl = EnvUtils.get("OPENAI_API_URL");
    if (serverUrl == null) {
      serverUrl = OpenAiConstatns.server_url;
    }

    serverUrl += "/v1";
    return chatCompletions(serverUrl, header, bodyString);
  }

  public static Response chatCompletions(String bodyString) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return chatCompletions(apiKey, bodyString);
  }

  public static Call chatCompletions(String bodyString, Callback callback) {
    Map<String, String> header = new HashMap<>(1);
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    header.put("Authorization", "Bearer " + apiKey);
    return chatCompletions(header, bodyString, callback);
  }

  public static Call chatCompletions(Map<String, String> header, String bodyString, Callback callback) {
    String serverUrl = EnvUtils.get("OPENAI_API_URL");
    if (serverUrl == null) {
      serverUrl = OpenAiConstatns.server_url;
    }
    serverUrl += "/v1";
    return chatCompletions(serverUrl, header, bodyString, callback);
  }

  public static ChatResponseVo chatCompletionsWithRole(String role, String prompt) {
    ChatMessage chatMessage = new ChatMessage(role, prompt);
    return chatCompletions(OpenAiModels.gpt_4o_mini, chatMessage);
  }

  public static ChatResponseVo chatCompletionsByModel(String model, String role, String prompt) {
    ChatMessage chatMessage = new ChatMessage(role, prompt);
    return chatCompletions(model, chatMessage);
  }

  public static ChatResponseVo chatCompletions(String model, ChatMessage chatMessage) {
    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel(model);
    chatRequestVo.setStream(false);
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(chatMessage);

    chatRequestVo.setMessages(messages);
    return chatCompletions(chatRequestVo);
  }

  public static ChatResponseVo chatCompletions(OpenAiChatRequestVo chatRequestVo) {
    String json = JsonUtils.toJson(chatRequestVo);
    ChatResponseVo respVo = null;
    try (Response response = chatCompletions(json)) {
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, ChatResponseVo.class);
      } else {
        throw new RuntimeException(bodyString);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return respVo;
  }

  public static Call chatCompletions(OpenAiChatRequestVo chatRequestVo, Callback callback) {
    String json = JsonUtils.toJson(chatRequestVo);
    return chatCompletions(json, callback);
  }

  public static ChatResponseVo chatCompletions(String apiPerfixUrl, String apiKey, OpenAiChatRequestVo chatRequestVo) {
    String json = JsonUtils.toJson(chatRequestVo);
    ChatResponseVo respVo = null;
    try (Response response = chatCompletions(apiPerfixUrl, apiKey, json)) {
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, ChatResponseVo.class);
      } else {
        throw new RuntimeException(bodyString);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return respVo;
  }

  public static Response chatCompletions(String apiPerfixUrl, String apiKey, String bodyString) {
    Map<String, String> header = new HashMap<>(1);
    header.put("Authorization", "Bearer " + apiKey);
    return chatCompletions(apiPerfixUrl, header, bodyString);
  }

  public static Call chatCompletions(String apiPerfixUrl, String apiKey, String bodyString, Callback callback) {
    Map<String, String> header = new HashMap<>(1);
    header.put("Authorization", "Bearer " + apiKey);
    return chatCompletions(apiPerfixUrl, header, bodyString, callback);
  }

  public static Response chatCompletions(String uri, Map<String, String> requestHeaders, String bodyString) {

    OkHttpClient httpClient = OkHttpClientPool.getHttpClient();

    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(mediaType, bodyString);

    Headers headers = Headers.of(requestHeaders);

    String url = uri + "/chat/completions";
    Request request = new Request.Builder() //
        .url(url) //
        .method("POST", body).headers(headers) //
        .build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Call chatCompletions(String serverUrl, String apiKey, OpenAiChatRequestVo chatRequestVo, Callback callback) {
    return chatCompletions(serverUrl, apiKey, JsonUtils.toJson(chatRequestVo), callback);
  }

  public static Call chatCompletions(String apiPrefixUrl, Map<String, String> requestHeaders, String bodyString, Callback callback) {
    OkHttpClient httpClient = OkHttpClientPool.getHttpClient();

    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(mediaType, bodyString);

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

  public static Response embeddings(String bodyString) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return embeddings(apiKey, bodyString);
  }

  public static Response embeddings(String apiKey, String bodyString) {
    String serverUrl = EnvUtils.get("OPENAI_API_URL");
    return embeddings(serverUrl, apiKey, bodyString);
  }

  public static Response embeddings(String serverUrl, String apiKey, String bodyString) {
    if (serverUrl == null) {
      serverUrl = OpenAiConstatns.server_url;
    }

    OkHttpClient httpClient = OkHttpClientPool.getHttpClient();

    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(mediaType, bodyString);

    Map<String, String> requestHeaders = new HashMap<>(1);
    requestHeaders.put("Authorization", "Bearer " + apiKey);

    Headers headers = Headers.of(requestHeaders);

    Request request = new Request.Builder() //
        .url(serverUrl + "/v1/embeddings") //
        .method("POST", body).headers(headers) //
        .build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static float[] embeddingArray(String input, String model) {
    EmbeddingRequestVo embeddingRequestVo = new EmbeddingRequestVo(input, model);
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return embeddings(apiKey, embeddingRequestVo).getData().get(0).getEmbedding();
  }

  public static float[] embeddingArray(String serverUrl, String input, String model) {
    EmbeddingRequestVo embeddingRequestVo = new EmbeddingRequestVo(input, model);
    return embeddings(serverUrl, embeddingRequestVo).getData().get(0).getEmbedding();
  }

  public static float[] embeddingArray(String input) {
    return embeddingArray(input, OpenAiModels.text_embedding_3_small);
  }

  public static float[] embeddingArrayByLargeModel(String input) {
    return embeddingArray(input, OpenAiModels.text_embedding_3_large);
  }

  public static EmbeddingResponseVo embeddings(EmbeddingRequestVo embeddingRequestVo) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return embeddings(apiKey, embeddingRequestVo);
  }

  public static EmbeddingResponseVo embeddings(String serverUrl, String apiKey, EmbeddingRequestVo reoVo) {
    EmbeddingResponseVo respVo = null;
    try (Response response = embeddings(serverUrl, apiKey, JsonUtils.toJson(reoVo))) {
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
    try (Response response = embeddings(apiKey, JsonUtils.toJson(reoVo))) {
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, EmbeddingResponseVo.class);
      } else {
        throw new RuntimeException("status:" + response.code() + ",body:" + bodyString);
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

}