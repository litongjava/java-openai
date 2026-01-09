package com.litongjava.genie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;

public class GenieClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    String text = "天空为什么是蓝色的？这是一个困扰了人类很久的问题。当太阳光照射到地球大气层时，会发生一系列复杂的物理现象。我们看到的蓝天，实际上是阳光与大气中微小粒子相互作用的结果。让我们一起探索这个美丽现象背后的科学原理。";
    GenieClient genieClient = new GenieClient();
    GenieTTSRequest reqVo = new GenieTTSRequest("feibi", text);
    ResponseVo responseVo = genieClient.tts(reqVo);
    if (responseVo.isOk()) {
      try {
        Files.write(Paths.get("output.wav"), responseVo.getBodyBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

}
