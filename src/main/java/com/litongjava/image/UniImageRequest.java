package com.litongjava.image;

import com.litongjava.gitee.GiteeModels;
import com.litongjava.openai.consts.OpenAiModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UniImageRequest {
  private String apiKey;
  private String platform;

  private String model;
  private String prompt;
  private String size;
  private String user;
  private Integer n;
  private String response_format;

  public UniImageRequest(String model, String prompt) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    // this.response_format = "url";
  }

  public UniImageRequest(String model, String prompt, String size) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    this.size = size;
  }

  public UniImageRequest(String model, String prompt, String size, String response_format) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    this.size = size;
    this.response_format = response_format;
  }

  public static UniImageRequest buildGptImage1(String prompt) {
    return new UniImageRequest(OpenAiModels.GPT_IMAGE_1, prompt);
  }

  public static UniImageRequest buildQwenImage(String prompt) {
    return new UniImageRequest(GiteeModels.QWEN_IMAGE, prompt);
  }
}
