package com.litongjava.gitee.image;

import com.litongjava.gitee.GiteeModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GiteeImageRequest {
  private String model;
  private String prompt;
  private String size;
  private String user;
  private Integer n;
  private String response_format;

  public GiteeImageRequest(String model, String prompt) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    this.response_format = "url";
  }

  public static GiteeImageRequest buildQwenImage(String prompt) {
    return new GiteeImageRequest(GiteeModels.QWEN_IMAGE, prompt);
  }
}
