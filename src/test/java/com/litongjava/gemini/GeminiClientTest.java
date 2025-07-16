package com.litongjava.gemini;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;

public class GeminiClientTest {

  @Test
  public void youtube() {
    EnvUtils.load();
    String model = GoogleGeminiModels.GEMINI_2_5_FLASH;
    String url = "https://www.youtube.com/watch?v=31FpW6CMmYE";
    String userPrompt = "Extract video subtitles, output format [hh:mm:ss-hh:mm:ss] subtitle";
    String text = GeminiClient.parseYoutubeSubtitle(model, url, userPrompt);
    System.out.println(text);
  }
}
