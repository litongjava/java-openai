package nexus.io.chat;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpServer;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;
import static org.junit.Assert.*;

public class UniChatRawTransportTest {
  @Test
  public void preservesErrorStatusHeadersAndBody() throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    server.createContext("/messages", exchange -> {
      exchange.getResponseHeaders().set("x-test", "raw");
      byte[] bytes = "{\"error\":\"limited\"}".getBytes(StandardCharsets.UTF_8);
      exchange.sendResponseHeaders(429, bytes.length);
      try (OutputStream output = exchange.getResponseBody()) {
        output.write(bytes);
      }
    });
    server.start();
    try {
      Request request = new Request.Builder().url("http://127.0.0.1:" + server.getAddress().getPort() + "/messages")
          .post(RequestBody.create("{\"custom\":true}", MediaType.get("application/json"))).build();
      try (Response response = UniChatClient.execute(new OkHttpClient(), request)) {
        assertEquals(429, response.code());
        assertEquals("raw", response.header("x-test"));
        assertEquals("{\"error\":\"limited\"}", response.body().string());
      }
      assertFalse(UniChatClient.newCall(new OkHttpClient(), request).isExecuted());
    } finally {
      server.stop(0);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void rejectsMissingTransport() {
    UniChatClient.newCall(null, new Request.Builder().url("http://localhost/").build());
  }
}
