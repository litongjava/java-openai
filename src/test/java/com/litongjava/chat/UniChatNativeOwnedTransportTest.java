package com.litongjava.chat;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.tio.utils.environment.EnvUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class UniChatNativeOwnedTransportTest {
  // platform, URL setting, key setting, default prefix, protocol
  private static final String[][] ROUTES = {
      {"google", "GEMINI_API_URL", "GEMINI_API_KEY", "https://generativelanguage.googleapis.com/v1beta/models", "google"},
      {"vertex_ai", "VERTEX_AI_API_URL", "VERTEX_AI_API_KEY", "https://aiplatform.googleapis.com/v1/publishers/google/models", "google"},
      {"exchange_token_google", "EXCHANGE_TOKEN_GOOGLE_API_URL", "EXCHANGE_TOKEN_API_KEY", "https://api.exchangetoken.ai/v1beta/models", "google"},
      {"exchange_token_us_google", "EXCHANGE_TOKEN_US_GOOGLE_API_URL", "EXCHANGE_TOKEN_API_KEY", "https://api-us.exchangetoken.ai/v1beta/models", "google"},
      {"anthropic", "CLAUDE_API_URL", "CLAUDE_API_KEY", "https://api.anthropic.com/v1", "claude"},
      {"exchange_token_anthropic", "EXCHANGE_TOKEN_API_URL", "EXCHANGE_TOKEN_API_KEY", "https://api.exchangetoken.ai/v1", "claude"},
      {"exchange_token_us_anthropic", "EXCHANGE_TOKEN_US_API_URL", "EXCHANGE_TOKEN_API_KEY", "https://api-us.exchangetoken.ai/v1", "claude"},
      {"openai_responses", "OPENAI_RESPONSES_API_URL", "OPENAI_RESPONSES_API_KEY", "https://api.openai.com/v1", "responses"},
      {"volcengine_responses", "VOLCENGINE_RESPONSES_API_URL", "VOLCENGINE_RESPONSES_API_KEY", "https://ark.cn-beijing.volces.com/api/v3", "responses"}
  };

  private UniChatRequest request(String platform) {
    return new UniChatRequest(platform, "test-model").setStream(false).setSystemPrompt("system rules")
        .setMax_tokens(123).setMessages(Collections.singletonList(new UniChatMessage("model", "history")));
  }

  private String suffix(String protocol) {
    return "google".equals(protocol) ? "/test-model:generateContent" : "claude".equals(protocol) ? "/messages" : "/responses";
  }

  private String header(String protocol) {
    return "google".equals(protocol) ? "x-goog-api-key" : "claude".equals(protocol) ? "x-api-key" : "Authorization";
  }

  private String fixture(String protocol) {
    if ("google".equals(protocol)) {
      return "{\"modelVersion\":\"returned-model\",\"candidates\":[{\"content\":{\"role\":\"model\",\"parts\":[{\"text\":\"ok\"}]}}],\"usageMetadata\":{\"promptTokenCount\":2,\"candidatesTokenCount\":3,\"totalTokenCount\":5}}";
    } else if ("claude".equals(protocol)) {
      return "{\"model\":\"returned-model\",\"role\":\"assistant\",\"content\":[{\"type\":\"text\",\"text\":\"ok\"}],\"usage\":{\"input_tokens\":2,\"output_tokens\":3}}";
    }
    return "{\"model\":\"returned-model\",\"status\":\"completed\",\"output\":[{\"type\":\"message\",\"content\":[{\"type\":\"output_text\",\"text\":\"ok\"}]}],\"usage\":{\"input_tokens\":2,\"output_tokens\":3,\"total_tokens\":5}}";
  }

  private Response response(okhttp3.Request request, int status, String body) {
    return new Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(status).message("test")
        .body(ResponseBody.create(MediaType.parse("application/json"), body)).build();
  }

  private void assertPayload(String protocol, JSONObject body) {
    if ("google".equals(protocol)) {
      assertNull(body.get("messages"));
      assertEquals("model", body.getJSONArray("contents").getJSONObject(0).getString("role"));
      assertNotNull(body.getJSONObject("system_instruction"));
      assertEquals(123, body.getJSONObject("generationConfig").getIntValue("maxOutputTokens"));
    } else if ("claude".equals(protocol)) {
      assertEquals("test-model", body.getString("model"));
      assertEquals("assistant", body.getJSONArray("messages").getJSONObject(0).getString("role"));
      assertNotNull(body.getJSONArray("system"));
      assertEquals(123, body.getIntValue("max_tokens"));
    } else {
      assertNull(body.get("messages"));
      assertEquals("test-model", body.getString("model"));
      assertEquals("system", body.getJSONArray("input").getJSONObject(0).getString("role"));
      assertEquals("assistant", body.getJSONArray("input").getJSONObject(1).getString("role"));
      assertEquals(123, body.getIntValue("max_output_tokens"));
    }
  }

  @Test public void routesAllNativeProtocolsThroughOwnedClientWithPlatformCredentials() throws Exception {
    for (String[] route : ROUTES) {
      String oldUrl=EnvUtils.get(route[1]), oldKey=EnvUtils.get(route[2]);
      AtomicInteger calls=new AtomicInteger();
      try {
        EnvUtils.set(route[1], "https://configured.example/native");
        EnvUtils.set(route[2], "platform-key");
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain -> {
          calls.incrementAndGet();
          assertEquals("https://configured.example/native"+suffix(route[4]), chain.request().url().toString());
          assertEquals("responses".equals(route[4])?"Bearer platform-key":"platform-key", chain.request().header(header(route[4])));
          if ("claude".equals(route[4])) { assertEquals("2023-06-01", chain.request().header("anthropic-version")); }
          if (!"responses".equals(route[4])) { assertNull(chain.request().header("Authorization")); }
          Buffer buffer=new Buffer(); chain.request().body().writeTo(buffer);
          assertPayload(route[4], JSON.parseObject(buffer.readUtf8()));
          return response(chain.request(), 200, fixture(route[4]));
        }).build();
        UniChatRequest request=request(route[0]);
        UniChatResponse result=UniChatClient.generate(client, request);
        assertEquals("ok", result.getMessage().getContent());
        assertEquals(Integer.valueOf(5), result.getUsage().getTotal_tokens());
        assertEquals(fixture(route[4]), result.getRawData());
        assertEquals("ok", UniChatClient.generate(client, null, request).getMessage().getContent());
        assertEquals("model", request.getMessages().get(0).getRole());
        assertNull(request.getApiPrefixUrl());
        assertNull(request.getApiKey());
        assertEquals(2, calls.get());
      } finally { EnvUtils.set(route[1], oldUrl); EnvUtils.set(route[2], oldKey); }
    }
  }

  @Test public void resolvesNativeDefaultEndpointsWithoutRequestPrefix() throws Exception {
    for (String[] route : ROUTES) {
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain -> {
        assertEquals(route[3]+suffix(route[4]), chain.request().url().toString());
        return response(chain.request(), 200, fixture(route[4]));
      }).build();
      UniChatClient.generate(client, "explicit-key", request(route[0]));
    }
  }

  @Test public void nativeExplicitUrlAndKeysTakePriority() throws Exception {
    for (String[] route : ROUTES) {
      for (String key : new String[]{null, "explicit-key"}) {
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain -> {
          assertEquals("https://override.example/api"+suffix(route[4]), chain.request().url().toString());
          String expected=key==null?"request-key":key;
          if ("responses".equals(route[4])) { expected="Bearer "+expected; }
          assertEquals(expected, chain.request().header(header(route[4])));
          return response(chain.request(), 200, fixture(route[4]));
        }).build();
        UniChatClient.generate(client, key, request(route[0]).setApiPrefixUrl("https://override.example/api").setApiKey("request-key"));
      }
    }
  }

  @Test public void responsesKeysFallBackToBasePlatformKey() throws Exception {
    for (String platform : new String[]{"OPENAI", "VOLCENGINE"}) {
      String keyName=platform+"_RESPONSES_API_KEY", fallbackName=platform+"_API_KEY";
      String oldKey=EnvUtils.get(keyName), oldFallback=EnvUtils.get(fallbackName);
      try {
        EnvUtils.set(keyName, ""); EnvUtils.set(fallbackName, "fallback-key");
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain -> {
          assertEquals("Bearer fallback-key", chain.request().header("Authorization"));
          return response(chain.request(), 200, fixture("responses"));
        }).build();
        UniChatClient.generate(client, request(platform.toLowerCase()+"_responses"));
      } finally { EnvUtils.set(keyName, oldKey); EnvUtils.set(fallbackName, oldFallback); }
    }
  }

  @Test public void nativeErrorsStaySanitizedAndDoNotRetry() throws Exception {
    for (String platform : new String[]{"google", "anthropic", "openai_responses"}) {
      for (String body : new String[]{"private-provider-body", "{}", "null"}) {
        AtomicInteger calls=new AtomicInteger();
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain -> {
          calls.incrementAndGet(); return response(chain.request(), 200, body);
        }).build();
        try { UniChatClient.generate(client, "key", request(platform)); fail("must reject invalid response"); }
        catch (IOException expected) { assertFalse(expected.getMessage().contains(body)); }
        assertEquals(1, calls.get());
      }
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain -> response(chain.request(), 401, "private-error")).build();
      try { UniChatClient.generate(client, "key", request(platform)); fail("must reject HTTP failure"); }
      catch (IOException expected) { assertEquals("Chat provider HTTP status 401", expected.getMessage()); }
    }
  }

  @Test public void nativeProtocolsAcceptSuccessfulResponsesLargerThanOneMiB() throws Exception {
    String content=new String(new char[1048577]).replace('\0','x');
    for(String[] route:ROUTES) {
      String body=fixture(route[4]).replace("\"ok\"","\""+content+"\"");
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->response(chain.request(),200,body)).build();
      UniChatResponse result=UniChatClient.generate(client,"test-key",request(route[0]));
      assertEquals(content,result.getMessage().getContent());
      assertEquals(body,result.getRawData());
    }
  }

  @Test public void googleJsonModeAndClaudeTokenDefaultsAreProtocolSpecific() throws Exception {
    OkHttpClient google=new OkHttpClient.Builder().addInterceptor(chain -> {
      Buffer buffer=new Buffer(); chain.request().body().writeTo(buffer);
      JSONObject body=JSON.parseObject(buffer.readUtf8());
      assertEquals("application/json", body.getJSONObject("generationConfig").getString("responseMimeType"));
      assertNull(body.get("system_instruction"));
      return response(chain.request(), 200, fixture("google"));
    }).build();
    UniChatClient.generate(google, "key", request("google").setResponseFormat("json_object").setUseSystemPrompt(false));
    OkHttpClient claude=new OkHttpClient.Builder().addInterceptor(chain -> {
      Buffer buffer=new Buffer(); chain.request().body().writeTo(buffer);
      assertEquals(64000, JSON.parseObject(buffer.readUtf8()).getIntValue("max_tokens"));
      return response(chain.request(), 200, fixture("claude"));
    }).build();
    UniChatRequest request=request("anthropic").setMax_tokens(null);
    UniChatClient.generate(claude, "key", request);
    assertNull(request.getMax_tokens());
  }
}
