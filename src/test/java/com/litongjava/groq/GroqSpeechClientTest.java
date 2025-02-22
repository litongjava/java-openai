package com.litongjava.groq;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

public class GroqSpeechClientTest {

  @Test
  public void test() {
    String filePath = "path/to/your/audio.wav"; // Replace with your WAV file path
    String model = "whisper-large-v3"; // Choose the appropriate model
    String mimeType = "audio/wav";
    String fileName = new File(filePath).getName();

    try {
      byte[] audioData = Files.readAllBytes(new File(filePath).toPath());

      GroqSpeechClient.transcribe(audioData, model, mimeType, fileName, transcript -> {
        System.out.println("Transcription Result: " + transcript);
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
