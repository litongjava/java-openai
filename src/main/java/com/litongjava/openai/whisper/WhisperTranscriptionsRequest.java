package com.litongjava.openai.whisper;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://platform.openai.com/docs/api-reference/audio/createTranslation#audio_createtranslation-model
 * 
 * @author Tong Li
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhisperTranscriptionsRequest {

  private File file;
  private String model = "whisper-1";
  private String prompt;
  private String response_format;
  private Boolean stream;
  private Float temperature;

  public WhisperTranscriptionsRequest(String responseFormat) {
    this.response_format = responseFormat;
  }

  public WhisperTranscriptionsRequest(String responseFormat, String prompt) {
    this.response_format = responseFormat;
    this.prompt = prompt;

  }
}
