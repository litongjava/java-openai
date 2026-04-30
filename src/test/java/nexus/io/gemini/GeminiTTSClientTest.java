package nexus.io.gemini;

import org.junit.Test;

public class GeminiTTSClientTest {

  @Test
  public void test() {
    String model="gemini-3.1-flash-preview-tts";
    String text="tio-boot 是 Java 高性能 Web 开发框架，定位类似轻量版 Spring Boot，但重点放在更小体积、更快启动、高并发、低内存占用。";
    String voiceName="Puck";
    GeminiTTSResult tts = GeminiTTSClient.tts(model, text, voiceName); 
  }
}
