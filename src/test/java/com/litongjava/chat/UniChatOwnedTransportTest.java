package com.litongjava.chat;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class UniChatOwnedTransportTest {
  private UniChatRequest request() {
    return new UniChatRequest("deepseek","deepseek-flash")
        .setMessages(Collections.singletonList(UniChatMessage.buildUser("hello"))).setStream(false);
  }
  @Test public void usesOwnedTransportAndPreservesModel() throws Exception {
    AtomicInteger calls=new AtomicInteger();
    OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
      calls.incrementAndGet();
      assertEquals("https://api.deepseek.com/v1/chat/completions",chain.request().url().toString());
      assertEquals("Bearer test-key",chain.request().header("Authorization"));
      Buffer buffer=new Buffer();chain.request().body().writeTo(buffer);
      assertTrue(buffer.readUtf8().contains("deepseek-flash"));
      return response(chain.request(),200,"{\"model\":\"deepseek-flash\",\"choices\":[{\"message\":{\"role\":\"assistant\",\"content\":\"ok\"}}]}");
    }).build();
    assertEquals("ok",UniChatClient.generate(client,"test-key",request()).getMessage().getContent());
    assertEquals(1,calls.get());
  }
  @Test public void sanitizesErrorAndDoesNotRetry() throws Exception {
    AtomicInteger calls=new AtomicInteger();
    OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
      calls.incrementAndGet();return response(chain.request(),400,"private-provider-body test-key");
    }).build();
    try {UniChatClient.generate(client,"test-key",request());fail("must fail");}
    catch(IOException expected) {assertEquals("Chat provider HTTP status 400",expected.getMessage());}
    assertEquals(1,calls.get());
  }
  @Test public void rejectsMalformedResponses() throws Exception {
    for(String body:new String[]{"not json", "{}", "null"}) {
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->response(chain.request(),200,body)).build();
      try {UniChatClient.generate(client,"test-key",request());fail("must fail");}
      catch(IOException expected) {assertFalse(expected.getMessage().contains(body));}
    }
  }
  @Test public void acceptsSuccessfulResponseLargerThanOneMiB() throws Exception {
    String content=new String(new char[1048577]).replace('\0','x');
    String body="{\"choices\":[{\"message\":{\"role\":\"assistant\",\"content\":\""+content+"\"}}]}";
    OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->response(chain.request(),200,body)).build();
    nexus.io.chat.UniChatResponse result=UniChatClient.generate(client,"test-key",request());
    assertEquals(content,result.getMessage().getContent());
    assertEquals(body,result.getRawData());
  }
  @Test public void resolvesEveryCompatiblePlatformAndEnvironmentKey() throws Exception {
    String[][] platforms = {
      {"openai","OPENAI"}, {"deepseek","DEEPSEEK"}, {"volcengine","VOLCENGINE"},
      {"openrouter","OPENROUTER"}, {"zenmux","ZENMUX"}, {"bailian","BAILIAN"},
      {"tencent","TENCENT"}, {"minimax","MINIMAX"}, {"moonshot","MOONSHOT"},
      {"cerebras","CEREBRAS"}, {"ollama","OLLAMA"}, {"llamacpp","LLAMACPP"},
      {"vllm","VLLM"}, {"swift","SWIFT"}, {"titanium","TITANIUM"},
      {"gitee","GITEE"}, {"llm-proxy","LLM_PROXY"}, {"exchange_token","EXCHANGE_TOKEN"},
      {"exchange_token_us","EXCHANGE_TOKEN_US"}, {"aiapi","AIAPI"}, {"custom","OPENAI"}
    };
    for (String[] platform : platforms) {
      String urlName=platform[1]+"_API_URL";
      String keyName=("exchange_token_us".equals(platform[0])?"EXCHANGE_TOKEN":platform[1])+"_API_KEY";
      String oldUrl=nexus.io.tio.utils.environment.EnvUtils.get(urlName);
      String oldKey=nexus.io.tio.utils.environment.EnvUtils.get(keyName);
      try {
        nexus.io.tio.utils.environment.EnvUtils.set(urlName,"https://configured.example/"+platform[0]);
        nexus.io.tio.utils.environment.EnvUtils.set(keyName,"configured-key");
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
          assertEquals("https://configured.example/"+platform[0]+"/chat/completions",chain.request().url().toString());
          assertEquals("Bearer configured-key",chain.request().header("Authorization"));
          return response(chain.request(),200,"{\"choices\":[{\"message\":{\"content\":\"ok\"}}]}");
        }).build();
        UniChatRequest request=request().setPlatform(platform[0]);
        assertEquals("ok",UniChatClient.generate(client,request).getMessage().getContent());
        assertEquals("ok",UniChatClient.generate(client,null,request).getMessage().getContent());
        assertEquals("ok",UniChatClient.generate(client," ",request).getMessage().getContent());
        assertNull(request.getApiKey());
        assertNull(request.getApiPrefixUrl());
      } finally {
        nexus.io.tio.utils.environment.EnvUtils.set(urlName,oldUrl);
        nexus.io.tio.utils.environment.EnvUtils.set(keyName,oldKey);
      }
    }
  }

  @Test public void usesBuiltInEndpointsWithoutRequestPrefix() throws Exception {
    String[][] platforms={
      {"openai","OPENAI",UniChatClient.OPENAI_API_URL},
      {"deepseek","DEEPSEEK",UniChatClient.DEEPSEEK_API_URL},
      {"volcengine","VOLCENGINE",UniChatClient.VOLCENGINE_API_URL},
      {"openrouter","OPENROUTER",UniChatClient.OPENROUTER_API_URL},
      {"zenmux","ZENMUX",UniChatClient.ZENMUX_API_URL},
      {"bailian","BAILIAN",UniChatClient.BAILIAN_API_URL},
      {"tencent","TENCENT",UniChatClient.TENCENT_API_URL},
      {"minimax","MINIMAX",UniChatClient.MINIMAX_API_URL},
      {"moonshot","MOONSHOT",UniChatClient.MOONSHOT_API_URL},
      {"cerebras","CEREBRAS",UniChatClient.CEREBRAS_API_URL},
      {"gitee","GITEE",UniChatClient.GITEE_API_URL},
      {"llm-proxy","LLM_PROXY",UniChatClient.LLM_PROXY_API_URL},
      {"exchange_token","EXCHANGE_TOKEN",UniChatClient.EXCHANGE_TOKEN_API_URL},
      {"exchange_token_us","EXCHANGE_TOKEN_US",UniChatClient.EXCHANGE_TOKEN_US_API_URL},
      {"aiapi","AIAPI",UniChatClient.AIAPI_API_URL}
    };
    for(String[] platform:platforms) {
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
        assertEquals(platform[2]+"/chat/completions",chain.request().url().toString());
        return response(chain.request(),200,"{\"choices\":[{\"message\":{\"content\":\"ok\"}}]}");
      }).build();
      UniChatClient.generate(client,"test-key",request().setPlatform(platform[0]));
    }
  }

  @Test public void rejectsMissingClientRequestAndLocalEndpoint() throws Exception {
    OkHttpClient client=new OkHttpClient();
    try { UniChatClient.generate((OkHttpClient)null,request());fail("client required"); }
    catch(IllegalArgumentException expected) { assertEquals("Client and request are required",expected.getMessage()); }
    try { UniChatClient.generate(client,(UniChatRequest)null);fail("request required"); }
    catch(IllegalArgumentException expected) { assertEquals("Client and request are required",expected.getMessage()); }
    String old=nexus.io.tio.utils.environment.EnvUtils.get("OLLAMA_API_URL");
    try {
      nexus.io.tio.utils.environment.EnvUtils.set("OLLAMA_API_URL", "");
      try { UniChatClient.generate(client,request().setPlatform("ollama"));fail("endpoint required"); }
      catch(IllegalArgumentException expected) { assertTrue(expected.getMessage().contains("API prefix")); }
    } finally { nexus.io.tio.utils.environment.EnvUtils.set("OLLAMA_API_URL",old); }
  }

  @Test public void explicitUrlAndKeyOverridePlatformConfiguration() throws Exception {
    for (String key : new String[]{null,"explicit-key"}) {
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
        assertEquals("https://override.example/v1/chat/completions",chain.request().url().toString());
        assertEquals("Bearer "+(key==null?"request-key":key),chain.request().header("Authorization"));
        return response(chain.request(),200,"{\"choices\":[{\"message\":{\"content\":\"ok\"}}]}");
      }).build();
      UniChatRequest request=request().setApiPrefixUrl("https://override.example/v1").setApiKey("request-key");
      UniChatClient.generate(client,key,request);
      if(key==null) { UniChatClient.generate(client,request); }
    }
  }

  @Test public void unauthenticatedLocalPlatformOmitsAuthorization() throws Exception {
    String old=nexus.io.tio.utils.environment.EnvUtils.get("OLLAMA_API_KEY");
    try {
      nexus.io.tio.utils.environment.EnvUtils.set("OLLAMA_API_KEY", "");
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
        assertNull(chain.request().header("Authorization"));
        return response(chain.request(),200,"{\"choices\":[{\"message\":{\"content\":\"ok\"}}]}");
      }).build();
      UniChatClient.generate(client,request().setPlatform("ollama").setApiPrefixUrl("http://localhost:11434/v1"));
    } finally { nexus.io.tio.utils.environment.EnvUtils.set("OLLAMA_API_KEY",old); }
  }

  @Test public void preservesPlatformPayloadDefaultsWithoutMutatingRequest() throws Exception {
    for(String platform:new String[]{"volcengine","bailian"}) {
      OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
        Buffer buffer=new Buffer();chain.request().body().writeTo(buffer);
        com.alibaba.fastjson2.JSONObject payload=com.alibaba.fastjson2.JSON.parseObject(buffer.readUtf8());
        if("volcengine".equals(platform)) { assertEquals(16384,payload.getIntValue("max_tokens")); }
        else { assertFalse(payload.getBooleanValue("enable_thinking")); }
        return response(chain.request(),200,"{\"choices\":[{\"message\":{\"content\":\"ok\"}}]}");
      }).build();
      UniChatRequest request=request().setPlatform(platform).setApiPrefixUrl("https://example.com/v1").setEnable_thinking(true);
      UniChatClient.generate(client,"test-key",request);
      assertNull(request.getMax_tokens());
      assertTrue(request.getEnable_thinking());
    }
  }

  @Test public void rejectsStreamingBeforeCallingTransport() throws Exception {
    OkHttpClient client=new OkHttpClient.Builder().addInterceptor(chain->{
      fail("transport must not be called");return null;
    }).build();
    try { UniChatClient.generate(client,request().setStream(true));fail("must reject streaming"); }
    catch(IllegalArgumentException expected) { assertTrue(expected.getMessage().contains("synchronous generation")); }
  }

  private Response response(okhttp3.Request request,int status,String body) {
    return new Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(status).message("test")
        .body(ResponseBody.create(MediaType.parse("application/json"),body)).build();
  }
}
