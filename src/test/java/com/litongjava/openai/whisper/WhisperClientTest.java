package com.litongjava.openai.whisper;


import java.io.File;

import org.junit.Test;

import com.litongjava.consts.ModelPlatformName;
import com.litongjava.tio.utils.environment.EnvUtils;

import nexus.io.model.http.response.ResponseVo;

public class WhisperClientTest {
  @Test
  public void transcriptions() {
    EnvUtils.load();
    // String filename =
    // "E:\\code\\java\\project-litongjava\\java-kit-server\\videos\\02\\539850349333278720.mp3";
    String filename = "E:\\code\\java\\project-litongjava\\java-kit-server\\videos\\01\\main-v1.mp3";
    File file = new File(filename);
    ResponseVo responseVo = WhisperClient.transcriptions(file, WhisperResponseFormat.vtt);
    System.out.println(responseVo.getBodyString());
  }

  @Test
  public void transcriptionsToJson() {
    EnvUtils.load();
    // String filename =
    // "E:\\code\\java\\project-litongjava\\java-kit-server\\videos\\02\\539850349333278720.mp3";
    String filename = "E:\\code\\java\\project-litongjava\\java-kit-server\\videos\\01\\main-v1.mp3";
    File file = new File(filename);
    WhisperTranscriptionResponse transcriptToJson = WhisperClient.transcriptToJson(ModelPlatformName.AIAPI, file);
    System.out.println(transcriptToJson.getText());
  }
}