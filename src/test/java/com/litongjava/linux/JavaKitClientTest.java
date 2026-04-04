package com.litongjava.linux;

import java.io.File;

import org.junit.Test;

import com.litongjava.tio.utils.commandline.ProcessResult;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.json.JsonUtils;

import nexus.io.linux.ExecuteCodeRequest;
import nexus.io.linux.JavaKitClient;
import nexus.io.linux.SessionFinishRequest;

public class JavaKitClientTest {

  @Test
  public void testFinishRequest() {
    Long sessionId = 578029478896693248L;
    String[] sceneNames = { "Scene1", "Scene2", "Scene3", "Scene4", "Scene5", "Scene6" };
    SessionFinishRequest request = new SessionFinishRequest(sessionId, sceneNames);

    ProcessResult motionCanvasFinish = JavaKitClient.motionCanvasFinish("", "", request);
    System.out.println(JsonUtils.toJson(motionCanvasFinish));
  }

  @Test
  public void executeMotionCanvasCodeMultipart() {
    EnvUtils.load();
    String javaKitUrl = EnvUtils.getStr("java.kit.url");

    String code = FileUtil.readString(new File("examples", "Scene01.tsx"));
    ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
    executeCodeRequest.setSessionId(597361985752285184L).setTimeout(300).setId(597362099749273600L);
    executeCodeRequest.setCode(code);
    ProcessResult executeMotionCanvasCodeMultipart = JavaKitClient.executeMotionCanvasCodeMultipart(javaKitUrl,
        "123456", executeCodeRequest);
    long start = System.currentTimeMillis();
    System.out.println(JsonUtils.toJson(executeMotionCanvasCodeMultipart));
    long end = System.currentTimeMillis();
    System.out.println(end - start + "(ms)");
  }
}
