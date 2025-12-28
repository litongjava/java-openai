package com.litongjava.linux;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.commandline.ProcessResult;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.tio.utils.url.UrlUtils;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JavaKitClient {

  private static OkHttpClient client = OkHttpClientPool.get1000HttpClient();

  public static ProcessResult executePythonCode(ExecuteCodeRequest codeRequest) {
    String apiBase = EnvUtils.getStr("JAVA_KIT_BASE_URL");
    String key = EnvUtils.getStr("JAVA_KIT_API_KEY");

    String targetUrl = apiBase + "/python";
    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult executeMainmCode(ExecuteCodeRequest codeRequest) {
    String apiBase = EnvUtils.getStr("JAVA_KIT_BASE_URL");
    String key = EnvUtils.getStr("JAVA_KIT_API_KEY");

    String targetUrl = apiBase + "/manim/run";
    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult executeManimImageCode(String apiBase, String key, ExecuteCodeRequest codeRequest) {
    String targetUrl = apiBase + "/manim/image";
    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult startManimSession(String apiBase, String key, long sessionId) {
    String targetUrl = apiBase + "/manim/start?session-id=" + sessionId;
    return get(targetUrl, key);
  }

  public static ProcessResult startManimSession(String apiBase, String key) {
    String targetUrl = apiBase + "/manim/start";
    return get(targetUrl, key);
  }

  public static ProcessResult finishManimSession(String apiBase, String key, VideoFinishRequest request) {
    String queryString = toQueryString(request);
    String targetUrl = apiBase + "/manim/finish?" + queryString;
    return get(targetUrl, key);
  }

  public static String toQueryString(VideoFinishRequest request) {
    StringBuilder sb = new StringBuilder();

    Long session_id = request.getSession_id();
    String watermark = request.getWatermark();
    String storage_platform = request.getStorage_platform();
    if (session_id != null) {
      sb.append("session_id=").append(session_id).append("&");
    }
    if (watermark != null && !watermark.isEmpty()) {
      String encode = UrlUtils.encode(watermark);
      sb.append("watermark=").append(encode).append("&");
    }
    if (storage_platform != null && !storage_platform.isEmpty()) {
      sb.append("storage_platform=").append(storage_platform).append("&");
    }

    if (sb.length() > 0) {
      sb.setLength(sb.length() - 1);
    }
    return sb.toString();
  }

  private static String toQueryString(ExecuteCodeRequest codeRequest) {
    Long session_id = codeRequest.getSessionId();
    Long code_id = codeRequest.getId();
    Integer code_timeout = codeRequest.getTimeout();
    String quality = codeRequest.getQuality();
    String storage_platform = codeRequest.getStorage_platform();

    StringBuilder sb = new StringBuilder();

    if (session_id != null) {
      sb.append("session_id=").append(session_id).append("&");
    }
    if (code_id != null) {
      sb.append("code_id=").append(code_id).append("&");
    }

    if (code_timeout != null) {
      sb.append("code_timeout=").append(code_timeout).append("&");
    }

    if (quality != null) {
      sb.append("quality=").append(quality).append("&");
    }

    if (storage_platform != null && !storage_platform.isEmpty()) {
      sb.append("storage_platform=").append(storage_platform).append("&");
    }

    if (sb.length() > 0) {
      sb.setLength(sb.length() - 1);
    }
    return sb.toString();

  }

  public static ProcessResult executeManimCode(String apiBase, String key, ExecuteCodeRequest codeRequest) {
    String targetUrl = apiBase + "/manim/run";
    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult executeMainmCode(String apiBase, ExecuteCodeRequest codeRequest) {
    String key = EnvUtils.getStr("JAVA_KIT_API_KEY");
    String targetUrl = apiBase + "/manim/run";
    return post(targetUrl, key, codeRequest);
  }

  public static ResponseVo delete(String apiBaseUrl, String key, String path) {
    String url = apiBaseUrl + "/delete?path=" + path;
    return HttpUtils.get(url, key);
  }

  public static ProcessResult executeMotionCanvasCode(String apiBase, String key, ExecuteCodeRequest codeRequest) {
    String targetUrl = apiBase + "/motion-canvas/run";

    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult motionCanvasFinish(String apiBase, String key, SessionFinishRequest codeRequest) {
    String targetUrl = apiBase + "/motion-canvas/finish";

    return post(targetUrl, key, codeRequest);
  }

  public static ResponseVo downloadVideo(String apiBaseUrl, String key, String m3u8Path) {
    String url = apiBaseUrl + "/video/download?path=" + m3u8Path;
    return download(url, key);
  }

  /**
   * downlaod.
   * 
   * @param url
   * @return
   */
  public static ResponseVo download(String url, String key) {
    Request request = new Request.Builder().url(url).addHeader("authorization", "Bearer " + key).get().build();

    try (Response response = OkHttpClientPool.getHttpClient().newCall(request).execute()) {
      Headers headers = response.headers();

      int code = response.code();
      if (response.isSuccessful()) {
        byte[] bytes = response.body().bytes();
        ResponseVo responseVo = ResponseVo.ok(headers, bytes);
        responseVo.setCode(code);
        return responseVo;
      } else {
        // not 2xx
        String bodyString = response.body() != null ? response.body().string() : "";
        ResponseVo responseVo = ResponseVo.fail(headers, bodyString);
        responseVo.setCode(code);
        return responseVo;
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to download content from " + url, e);
    }
  }

  private static ProcessResult get(String targetUrl, String key) {
    Request request = new Request.Builder().url(targetUrl).addHeader("authorization", "Bearer " + key).get().build();
    Call call = client.newCall(request);
    long start = System.currentTimeMillis();
    try (Response response = call.execute()) {
      String string = response.body().string();
      int resposneCode = response.code();
      if (response.isSuccessful()) {
        long end = System.currentTimeMillis();
        ProcessResult result = JsonUtils.parse(string, ProcessResult.class);
        if (result != null) {
          result.setElapsed(end - start);
        }
        return result;
      } else {
        throw new RuntimeException("code:" + resposneCode + " response:" + string);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + targetUrl, e);
    }
  }

  private static ProcessResult post(String targetUrl, String key, ExecuteCodeRequest codeRequest) {

    String code = codeRequest.getCode();
    String figure = codeRequest.getFigure();
    targetUrl = targetUrl + "?" + toQueryString(codeRequest);

    Request.Builder builder = new Request.Builder();
    builder.url(targetUrl).addHeader("authorization", "Bearer " + key);

    if (figure == null) {
      MediaType mediaType = MediaType.parse("text/plain");
      RequestBody body = RequestBody.create(code, mediaType);
      builder.method("POST", body);

    } else {
      okhttp3.MultipartBody.Builder formBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
      // Create the request body with file and image media type
      RequestBody fileBody = getFileBody("main.py", code);
      // Create MultipartBody
      formBuilder.addFormDataPart("code", "main.py", fileBody);

      RequestBody figureBody = getFileBody("pgdp-output.json", figure);
      // Create MultipartBody
      formBuilder.addFormDataPart("figure", "pgdp-output.json", figureBody);

      RequestBody requestBody = formBuilder.build();
      builder.method("POST", requestBody);
    }

    Request request = builder.build();
    long start = System.currentTimeMillis();
    Call call = client.newCall(request);
    try (Response response = call.execute()) {
      String string = response.body().string();
      int resposneCode = response.code();
      if (response.isSuccessful()) {
        long end = System.currentTimeMillis();
        ProcessResult result = JsonUtils.parse(string, ProcessResult.class);
        if (result != null) {
          result.setElapsed(end - start);
        }
        return result;
      } else {
        throw new RuntimeException("code:" + resposneCode + " response:" + string);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + targetUrl + " body:" + code, e);
    }
  }

  private static ProcessResult post(String targetUrl, String key, SessionFinishRequest codeRequest) {
    String json = JsonUtils.toJson(codeRequest);

    long start = System.currentTimeMillis();

    OkHttpClient client = OkHttpClientPool.get600HttpClient();
    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(json, mediaType);
    Request request = new Request.Builder()
        //
        .url(targetUrl).post(body)
        //
        .addHeader("Authorization", "Bearer " + key).build();
    try (Response response = client.newCall(request).execute()) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        long end = System.currentTimeMillis();
        ProcessResult result = JsonUtils.parse(bodyString, ProcessResult.class);
        if (result != null) {
          result.setElapsed(end - start);
        }
        return result;
      } else {
        throw new RuntimeException("code:" + code + " response:" + bodyString);
      }

    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + targetUrl, e);
    }
  }

  private static RequestBody getFileBody(String filename, String content) {
    String contentType = ContentTypeUtils.getContentType(filename);
    RequestBody fileBody = RequestBody.create(content.getBytes(), MediaType.parse(contentType));
    return fileBody;
  }
}
