package com.litongjava.linux;

import org.junit.Test;

import com.litongjava.tio.utils.commandline.ProcessResult;
import com.litongjava.tio.utils.json.JsonUtils;

public class JavaKitClientTest {

  @Test
  public void testFinishRequest() {
    Long sessionId = 578029478896693248L;
    String[] sceneNames = { "Scene1", "Scene2", "Scene3", "Scene4", "Scene5", "Scene6" };
    SessionFinishRequest request = new SessionFinishRequest(sessionId, sceneNames);

    ProcessResult motionCanvasFinish = JavaKitClient.motionCanvasFinish("", "", request);
    System.out.println(JsonUtils.toJson(motionCanvasFinish));
  }
}
