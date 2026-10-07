package nexus.io.linux;

import static org.junit.Assert.*;
import java.lang.reflect.Method;
import org.junit.Test;
import nexus.io.tio.utils.commandline.ProcessResult;

public class JavaKitVideoSizeTest {
  @Test
  public void sendsSizeAndReadsBothResponseFormats() throws Exception {
    Method query = JavaKitClient.class.getDeclaredMethod("toQueryString", ExecuteCodeRequest.class);
    query.setAccessible(true);
    ExecuteCodeRequest request = new ExecuteCodeRequest("pass");
    request.setSize("mobile");
    assertTrue(((String) query.invoke(null, request)).contains("size=mobile"));
    ProcessResult legacy = JavaKitClient.parseProcessResult("{\"exitCode\":0,\"output\":\"/a.mp4\"}");
    ProcessResult wrapped = JavaKitClient.parseProcessResult(
        "{\"ok\":true,\"code\":1,\"data\":{\"exitCode\":0,\"output\":\"/a.mp4\"}}");
    assertEquals(legacy.getOutput(), wrapped.getOutput());
  }

  @Test(expected = IllegalStateException.class)
  public void rejectsFailureEnvelope() {
    JavaKitClient.parseProcessResult("{\"ok\":false,\"code\":0,\"msg\":\"Invalid size\"}");
  }
}
