package com.litongjava.image;

import com.litongjava.consts.ModelPlatformName;

public class GiteeImageClient {

  public static UniImageResponse generateImage(UniImageRequest request) {
    request.setPrompt(ModelPlatformName.GITEE);
    return UniImageClient.generateImage(request);
  }
}
