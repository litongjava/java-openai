package nexus.io.prompt;

import java.net.URL;

import nexus.io.tio.utils.hutool.FileUtil;
import nexus.io.tio.utils.hutool.ResourceUtil;

public class PromptResource {

  public static String readString(String filename) {
    URL resource = ResourceUtil.getResource("prompts/" + filename);
    if (resource != null) {
      return FileUtil.readString(resource);
    }
    return null;
  }
}
