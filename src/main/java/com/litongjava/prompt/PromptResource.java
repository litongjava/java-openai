package com.litongjava.prompt;

import java.net.URL;

import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.ResourceUtil;

public class PromptResource {

  public String readString(String filename) {
    URL resource = ResourceUtil.getResource(filename);
    if (resource != null) {
      return FileUtil.readString(resource);
    }
    return null;
  }
}
