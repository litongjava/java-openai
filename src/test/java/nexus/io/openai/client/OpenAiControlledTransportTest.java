package nexus.io.openai.client;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Test;
import static org.junit.Assert.*;

public class OpenAiControlledTransportTest {
  @Test public void callerTransportAndHeadersArePreservedWithoutNetwork() throws Exception {
    AtomicInteger calls=new AtomicInteger();
    OkHttpClient client=new OkHttpClient.Builder().retryOnConnectionFailure(false).addInterceptor(chain->{
      calls.incrementAndGet();
      assertEquals("https://example.invalid/v1/chat/completions",chain.request().url().toString());
      assertEquals("original-operation",chain.request().header("Idempotency-Key"));
      return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
          .code(200).message("OK").body(ResponseBody.create("{}",MediaType.parse("application/json"))).build();
    }).build();
    try (Response response=OpenAiClient.chatCompletions(client,"https://example.invalid/v1",
        Collections.singletonMap("Idempotency-Key","original-operation"),"{}")) {
      assertEquals(200,response.code());assertEquals("{}",response.body().string());
    }
    assertEquals(1,calls.get());assertFalse(client.retryOnConnectionFailure());
  }
  @Test public void nullClientFailsBeforeSending() {
    try {
      OpenAiClient.chatCompletions(null,"https://example.invalid",Collections.emptyMap(),"{}");
      fail("client required");
    } catch (IllegalArgumentException expected) { assertEquals("httpClient is required",expected.getMessage()); }
  }
}
