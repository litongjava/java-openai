package com.litongjava.openai.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatRequestVo;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.constants.OpenAiConstatns;
import com.litongjava.openai.constants.OpenAiModels;
import com.litongjava.openai.embedding.EmbeddingRequestVo;
import com.litongjava.openai.embedding.EmbeddingResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAiClient {
  public static Response embeddings(String bodyString) {
    Map<String, String> header = new HashMap<>(1);
    String authorization = EnvUtils.get("OPENAI_API_KEY");
    header.put("authorization", "Bearer " + authorization);
    return embeddings(header, bodyString);
  }

  public static Response embeddings(Map<String, String> requestHeaders, String bodyString) {
    String serverUrl = EnvUtils.get("OPENAI_API_URL");
    if (serverUrl == null) {
      serverUrl = OpenAiConstatns.server_url;
    }

    OkHttpClient httpClient = OkHttpClientPool.getHttpClient();

    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(mediaType, bodyString);

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

  public static EmbeddingResponseVo embeddings(EmbeddingRequestVo reoVo) {
    EmbeddingResponseVo respVo = null;
    try (Response response = embeddings(JsonUtils.toJson(reoVo))) {
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        respVo = JsonUtils.parse(bodyString, EmbeddingResponseVo.class);
      } else {
        throw new RuntimeException(bodyString);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return respVo;
  }

  public static Float[] embeddingArray(String input, String model) {
    EmbeddingRequestVo embeddingRequestVo = new EmbeddingRequestVo(input, model);

    return embeddings(embeddingRequestVo).getData().get(0).getEmbedding();
  }

  public static Float[] embeddingArray(String input) {
    return embeddingArray(input, OpenAiModels.text_embedding_3_small);
  }

  public static Response chatCompletions(Map<String, String> requestHeaders, String bodyString) {

    String serverUrl = EnvUtils.get("OPENAI_API_URL");
    if (serverUrl == null) {
      serverUrl = OpenAiConstatns.server_url;
    }

    OkHttpClient httpClient = OkHttpClientPool.getHttpClient();

    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(mediaType, bodyString);

    Headers headers = Headers.of(requestHeaders);

    Request request = new Request.Builder() //
        .url(serverUrl + "/v1/chat/completions") //
        .method("POST", body).headers(headers) //
        .build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Response chatCompletions(String bodyString) {
    Map<String, String> header = new HashMap<>(1);
    String authorization = EnvUtils.get("OPENAI_API_KEY");
    header.put("authorization", "Bearer " + authorization);
    return chatCompletions(header, bodyString);
  }

  public static void chatCompletions(String bodyString, Callback callback) {
    Map<String, String> header = new HashMap<>(1);
    String authorization = EnvUtils.get("OPENAI_API_KEY");
    header.put("authorization", "Bearer " + authorization);
    chatCompletions(header, bodyString, callback);
  }

  public static void chatCompletions(Map<String, String> requestHeaders, String bodyString, Callback callback) {
    OkHttpClient httpClient = OkHttpClientPool.getHttpClient();

    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(mediaType, bodyString);

    Headers headers = Headers.of(requestHeaders);

    Request request = new Request.Builder() //
        .url(OpenAiConstatns.server_url + "/v1/chat/completions") //
        .method("POST", body).headers(headers) //
        .build();
    httpClient.newCall(request).enqueue(callback);
  }

  public static ChatResponseVo chatCompletions(String bodyString, String prompt) {
    ChatMessage chatMessage = new ChatMessage("system", prompt);
    return chatCompletions(OpenAiModels.gpt_4o_2024_05_13, chatMessage);
  }

  public static ChatResponseVo chatCompletions(String model, String bodyString, String prompt) {
    ChatMessage chatMessage = new ChatMessage("system", prompt);
    return chatCompletions(model, chatMessage);
  }

  public static ChatResponseVo chatCompletions(String model, ChatMessage chatMessage) {
    ChatRequestVo chatRequestVo = new ChatRequestVo();
    chatRequestVo.setModel(model);
    chatRequestVo.setStream(false);
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(chatMessage);

    chatRequestVo.setMessages(messages);
    return chatCompletions(chatRequestVo);
  }

  public static ChatResponseVo chatCompletions(ChatRequestVo chatRequestVo) {
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
}