package com.litongjava.gitee;

import static org.junit.Assert.*;

import org.junit.Test;

import nexus.io.gitee.GiteeClient;
import nexus.io.gitee.GiteeModels;
import nexus.io.gitee.GiteeOcrResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class GiteeOcrClientTest {
  @Test
  public void sendsImageMultipartAndReadsActualProviderResponse() {
    OkHttpClient http = new OkHttpClient.Builder().addInterceptor(chain -> {
      assertEquals("/v1/images/ocr", chain.request().url().encodedPath());
      assertEquals("Bearer test-key", chain.request().header("Authorization"));
      Buffer body = new Buffer();
      chain.request().body().writeTo(body);
      String multipart = body.readUtf8();
      assertTrue(multipart.contains("name=\"image\"; filename=\"page.png\""));
      assertTrue(multipart.contains("HunyuanOCR"));
      return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
          .code(200).message("OK").body(ResponseBody.create(
              "{\"text_result\":\"# 河南省自然资源厅\",\"prompt\":\"markdown\"}", MediaType.get("application/json"))).build();
    }).build();
    GiteeOcrResponse result = new GiteeClient("test-key", "http://localhost", http)
        .ocr(new byte[] {1, 2, 3}, "page.png", GiteeModels.HUNYUAN_OCR);
    assertEquals("# 河南省自然资源厅", result.getText());
    assertEquals("# 河南省自然资源厅", result.getText_result());
    assertEquals("markdown", result.getPrompt());
  }

  @Test
  public void readsDocumentedTextResponseAndPreservesTaskPolling() {
    OkHttpClient http = new OkHttpClient.Builder().addInterceptor(chain -> {
      String body = chain.request().url().encodedPath().equals("/v1/images/ocr")
          ? "{\"text\":\"hello\"}"
          : "{\"task_id\":\"task-1\",\"status\":\"success\",\"output\":{\"text\":\"done\"}}";
      return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
          .code(200).message("OK").body(ResponseBody.create(body, MediaType.get("application/json"))).build();
    }).build();
    GiteeClient client = new GiteeClient("test-key", "http://localhost", http);
    assertEquals("hello", client.ocr(new byte[] {1}, "page.png", GiteeModels.HUNYUAN_OCR).getText());
    assertEquals("done", client.getTask("task-1").getOutput().getText());
  }

  @Test
  public void preservesProviderErrors() {
    OkHttpClient http = new OkHttpClient.Builder().addInterceptor(chain ->
        new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
            .code(400).message("Bad Request")
            .body(ResponseBody.create("unsupported image", MediaType.get("text/plain"))).build()).build();
    try {
      new GiteeClient("test-key", "http://localhost", http).ocr(new byte[] {1}, "page.png", GiteeModels.HUNYUAN_OCR);
      fail("Expected provider error");
    } catch (RuntimeException expected) {
      assertTrue(expected.getMessage().contains("code:400"));
      assertTrue(expected.getMessage().contains("unsupported image"));
    }
  }
}
