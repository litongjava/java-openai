package nexus.io.openai.whisper;

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
  // 
  private String model = "whisper-1";
  private String prompt;
  private String response_format;
  private String language;
  private Boolean stream;
  private Float temperature;

  public WhisperTranscriptionsRequest(String responseFormat) {
    this.response_format = responseFormat;
  }

  public WhisperTranscriptionsRequest(String responseFormat, String prompt) {
    this.response_format = responseFormat;
    this.prompt = prompt;

  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  public String getResponse_format() {
    return response_format;
  }

  public void setResponse_format(String response_format) {
    this.response_format = response_format;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Boolean getStream() {
    return stream;
  }

  public void setStream(Boolean stream) {
    this.stream = stream;
  }

  public Float getTemperature() {
    return temperature;
  }

  public void setTemperature(Float temperature) {
    this.temperature = temperature;
  }
}
