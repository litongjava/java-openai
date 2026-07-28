package nexus.io.volcengine;

import org.junit.Test;

import nexus.io.exception.GenerateException;
import nexus.io.tio.utils.environment.EnvUtils;

public class VolcTtsHttpClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    try {
      VolcTtsHttpClient.tts("我将描述这张大模型能力说明页面，它有明确的筛选说明及√、×的符号定义。该说明页的核心表格已明确，输入输出均按文本、图片、视频分类，三款模型的对应支持情况也已梳理完毕");
    }catch (GenerateException e) {
      System.out.println(e.getRequestBody());
      System.out.println(e.getResponseBody());
    }
    
  }

}
