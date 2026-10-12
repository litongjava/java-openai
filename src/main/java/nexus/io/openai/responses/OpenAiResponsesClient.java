package nexus.io.openai.responses;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import nexus.io.chat.ChatRequestUtils;
import nexus.io.consts.ModelPlatformName;
import nexus.io.exception.GenerateException;
import nexus.io.openai.consts.OpenAiConst;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.http.OkHttpClientPool;
import nexus.io.tio.utils.hutool.StrUtil;
import nexus.io.tio.utils.json.JsonUtils;
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
public class OpenAiResponsesClient {
  public static boolean debug = false;
  public static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);
  public static final String OPENAI_API_KEY = EnvUtils.get(OpenAiConst.OPENAI_API_KEY);

  /** Creates an editable request with hosted search required and source URLs included. */
  public static OpenAiResponsesRequest webSearchRequest(String model, String query) {
    return new OpenAiResponsesRequest().setModel(model)
        .setInput(Collections.singletonList(OpenAiResponsesInput.userText(query)))
        .setTools(Collections.singletonList(new OpenAiWebSearchTool()))
        .setTool_choice("required")
        .setInclude(Collections.singletonList("web_search_call.action.sources"));
  }

  public static OpenAiResponsesResponse webSearch(String model, String query) {
    return responses(webSearchRequest(model, query));
  }

  public static OpenAiResponsesResponse webSearch(String apiKey, String model, String query) {
    return responses(apiKey, webSearchRequest(model, query));
  }

  /** apiPrefixUrl includes /v1, but not /responses. */
  public static OpenAiResponsesResponse webSearch(String apiPrefixUrl, String apiKey, String model, String query) {
    return responses(apiPrefixUrl, apiKey, webSearchRequest(model, query));
  }

  public static OpenAiResponsesResponse responses(String model, String text) {
    OpenAiResponsesRequest request = new OpenAiResponsesRequest().setModel(model)
        .setInput(Collections.singletonList(OpenAiResponsesInput.userText(text)));
    return responses(request);
  }

  public static OpenAiResponsesResponse responsesWithImage(String model, String imageUrl, String text) {
    OpenAiResponsesInput input = OpenAiResponsesInput
        .user(Arrays.asList(OpenAiResponsesInputContent.imageUrl(imageUrl), OpenAiResponsesInputContent.text(text)));
    OpenAiResponsesRequest request = new OpenAiResponsesRequest().setModel(model)
        .setInput(Collections.singletonList(input));
    return responses(request);
  }

  public static OpenAiResponsesResponse responses(OpenAiResponsesRequest request) {
    String apiKey = EnvUtils.get(OpenAiConst.OPENAI_API_KEY);
    return responses(apiKey, request);
  }

  public static OpenAiResponsesResponse responses(String apiKey, OpenAiResponsesRequest request) {
    return responses(OPENAI_API_URL, apiKey, request);
  }

  public static OpenAiResponsesResponse responses(String apiPrefixUrl, String apiKey, OpenAiResponsesRequest request) {
    if (Boolean.TRUE.equals(request.getStream())) {
      throw new IllegalArgumentException("Use the EventSourceListener overload for streaming Responses requests");
    }
    String json = ChatRequestUtils.toSkipNullJson(request);
    try (Response response = responses(apiPrefixUrl, apiKey, json)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        OpenAiResponsesResponse resp = JsonUtils.parse(bodyString, OpenAiResponsesResponse.class);
        resp.setRawResponse(bodyString);
        return resp;
      }
      log.error("OpenAI Responses failed status url:{},code:{},response body:{}", apiPrefixUrl, code, bodyString);
      throw new GenerateException(ModelPlatformName.OPENAI, "OpenAI Responses failed", apiPrefixUrl, json, code,
          bodyString);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Response responses(String apiPrefixUrl, String apiKey, String bodyString) {
    if (StrUtil.isBlank(apiPrefixUrl)) {
      apiPrefixUrl = OpenAiConst.API_PREFIX_URL;
    }
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }

    Map<String, String> headers = new HashMap<>(1);
    headers.put("Authorization", "Bearer " + apiKey);
    return responses(apiPrefixUrl, headers, bodyString);
  }

  public static Response responses(String apiPrefixUrl, Map<String, String> requestHeaders, String bodyString) {
    OkHttpClient httpClient = OkHttpClientPool.get600HttpClient();
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));
    Headers headers = Headers.of(requestHeaders);
    String url = apiPrefixUrl + "/responses";

    if (debug) {
      log.info("{} {}", url, bodyString);
    }

    Request request = new Request.Builder().url(url).method("POST", body).headers(headers).build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /** Delivers native Responses SSE events, including search progress and citations. */
  public static EventSource responses(String apiPrefixUrl, String apiKey, OpenAiResponsesRequest request,
      EventSourceListener listener) {
    if (StrUtil.isBlank(apiKey)) {
      throw new IllegalArgumentException("api key can not empty");
    }
    if (StrUtil.isBlank(apiPrefixUrl)) {
      apiPrefixUrl = OpenAiConst.API_PREFIX_URL;
    }
    request.setStream(true);
    RequestBody body = RequestBody.create(ChatRequestUtils.toSkipNullJson(request), MediaType.parse("application/json"));
    Request httpRequest = new Request.Builder().url(apiPrefixUrl + "/responses")
        .header("Authorization", "Bearer " + apiKey).header("Accept", "text/event-stream")
        .post(body).build();
    return EventSources.createFactory(OkHttpClientPool.get600HttpClient()).newEventSource(httpRequest, listener);
  }

  public static EventSource responses(OpenAiResponsesRequest request, EventSourceListener listener) {
    return responses(OPENAI_API_URL, EnvUtils.get(OpenAiConst.OPENAI_API_KEY), request, listener);
  }
}
