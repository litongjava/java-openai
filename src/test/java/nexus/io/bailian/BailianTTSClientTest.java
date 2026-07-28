package nexus.io.bailian;

import org.junit.Test;

import nexus.io.bailian.tts.BailianTTSResponse;
import nexus.io.exception.GenerateException;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.json.JsonUtils;

public class BailianTTSClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    BailianTTSClient bailianTTSClient = new BailianTTSClient();
    try {
      String model = "cosyvoice-v3.5-plus";
      String voice = "cosyvoice-v3.5-plus-bailian-e46c4292e6504f808be470fe6f9c8b93";
      BailianTTSResponse response = bailianTTSClient.tts(model, voice, "今天天气怎么样?");
      System.out.println(JsonUtils.toJson(response));
    } catch (GenerateException e) {
      System.out.println(e.getRequestBody());
      System.out.println(e.getResponseBody());
    }

  }

}
