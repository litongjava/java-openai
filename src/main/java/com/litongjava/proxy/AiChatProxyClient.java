package com.litongjava.proxy;

import java.io.IOException;
import java.util.Map;

import com.litongjava.tio.utils.http.OkHttpClientPool;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

public class AiChatProxyClient {

  public static Response generate(String url, Map<String, String> requestHeaders, String bodyString) {

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Builder builder = new Request.Builder().url(url).method("POST", body);
    
    if (requestHeaders != null) {
      builder.headers(Headers.of(requestHeaders));
    }
    Request request = builder.build();

    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static EventSource stream(String url, Map<String, String> requestHeaders, String bodyString, EventSourceListener listener) {
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Builder builder = new Request.Builder().url(url).method("POST", body);
    if (requestHeaders != null) {
      builder.headers(Headers.of(requestHeaders));
    }
    Request request = builder.build();

    // 这里就发起 SSE 请求了
    EventSource source = EventSources.createFactory(httpClient).newEventSource(request, listener);
    return source;
  }
}
