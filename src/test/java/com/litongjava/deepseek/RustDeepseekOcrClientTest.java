package com.litongjava.deepseek;

import java.io.File;

import org.junit.Test;

import com.litongjava.openai.chat.OpenAiChatResponse;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class RustDeepseekOcrClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    File file = new File("C:\\Users\\Administrator\\Pictures\\Screenshots\\Screenshot 2025-08-03 200805.png");
    OpenAiChatResponse responsee = RustDeepseekOcrClient.ocr("Convert this document to markdown.", file);
    System.out.println(JsonUtils.toJson(responsee));
  }

}
