package nexus.io.gitee;

import java.io.File;
import java.io.IOException;

import nexus.io.chat.ChatModelResponse;
import nexus.io.model.http.response.ResponseVo;
import nexus.io.openai.client.OpenAiClient;
import nexus.io.openai.whisper.WhisperClient;
import nexus.io.openai.whisper.WhisperTranscriptionsRequest;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.http.ContentTypeUtils;
import nexus.io.tio.utils.http.OkHttpClientPool;
import nexus.io.tio.utils.hutool.FilenameUtils;
import nexus.io.tio.utils.json.JsonUtils;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * GiteeClient: 封装 Gitee AI 文档解析接口
 */
public class GiteeClient {

  private static final OkHttpClient client = OkHttpClientPool.get60HttpClient();

  private final String apiKey;
  private final String baseUrl;
  private final OkHttpClient httpClient;

  /**
   * 从环境配置获取：GITEE_API_KEY、GITEE_BASE_URL。
   */
  public GiteeClient() {
    this(EnvUtils.get("GITEE_API_KEY"), EnvUtils.get("GITEE_BASE_URL", GiteeConst.BASE_URL));
  }

  public GiteeClient(String apiKey) {
    this(apiKey, GiteeConst.BASE_URL);
  }

  public GiteeClient(String apiKey, String baseUrl) {
    this(apiKey, baseUrl, client);
  }

  /** Allows callers to configure timeouts and request/response interceptors. */
  public GiteeClient(String apiKey, String baseUrl, OkHttpClient httpClient) {
    this.apiKey = apiKey;
    this.baseUrl = baseUrl != null ? baseUrl : GiteeConst.BASE_URL;
    this.httpClient = java.util.Objects.requireNonNull(httpClient, "httpClient");
  }

  /**
   * 调用 /v1/async/documents/parse 接口（文件形式）
   */
  public GiteeTaskResponse parseDocument(File file, String model, String prompt) {
    GiteeDocumentParseRequest request = new GiteeDocumentParseRequest();
    request.setModel(model);
    request.setPrompt(prompt);
    return parseDocument(file, request);
  }

  public GiteeTaskResponse parseDocument(File file, GiteeDocumentParseRequest requestEntity) {
    String suffix = FilenameUtils.getSuffix(file.getName());
    String contentType = ContentTypeUtils.getContentType(suffix);
    RequestBody fileBody = RequestBody.create(file, MediaType.get(contentType));
    return parseDocument(fileBody, file.getName(), requestEntity);
  }

  /**
   * 调用 /v1/async/documents/parse 接口（字节数组形式）
   */
  public GiteeTaskResponse parseDocument(byte[] data, String filename) {
    GiteeDocumentParseRequest request = new GiteeDocumentParseRequest();
    request.setModel(GiteeModels.DEEPSEEK_OCR);
    request.setPrompt(GiteePromptConst.pdf_to_markdown_prompt);
    return parseDocument(data, filename, request);
  }

  public GiteeTaskResponse parseDocument(byte[] data, String filename, String model, String prompt) {
    GiteeDocumentParseRequest request = new GiteeDocumentParseRequest();
    request.setModel(model);
    request.setPrompt(prompt);
    return parseDocument(data, filename, request);
  }

  public GiteeTaskResponse parseDocument(byte[] data, String filename, GiteeDocumentParseRequest requestEntity) {
    String suffix = FilenameUtils.getSuffix(filename);
    String contentType = ContentTypeUtils.getContentType(suffix);
    RequestBody fileBody = RequestBody.create(data, MediaType.get(contentType));
    return parseDocument(fileBody, filename, requestEntity);
  }

  /**
   * 获取任务状态 /api/v1/task/{task_id}
   */
  public GiteeTaskResponse getTask(String taskId) {
    String url = baseUrl + "/api/v1/task/" + taskId;

    Request request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + apiKey).get().build();

    return execute(request);
  }

  /** Synchronous image OCR: POST /v1/images/ocr (for example HunyuanOCR). */
  public GiteeOcrResponse ocr(File image, String model) {
    String contentType = ContentTypeUtils.getContentType(FilenameUtils.getSuffix(image.getName()));
    return ocr(RequestBody.create(image, MediaType.get(contentType)), image.getName(), model);
  }

  public GiteeOcrResponse ocr(byte[] image, String filename, String model) {
    String contentType = ContentTypeUtils.getContentType(FilenameUtils.getSuffix(filename));
    return ocr(RequestBody.create(image, MediaType.get(contentType)), filename, model);
  }

  private GiteeOcrResponse ocr(RequestBody image, String filename, String model) {
    MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("model", model).addFormDataPart("image", filename, image).build();
    Request request = new Request.Builder().url(baseUrl + "/v1/images/ocr")
        .addHeader("Authorization", "Bearer " + apiKey).post(body).build();
    return execute(request, GiteeOcrResponse.class);
  }

  /**
   * 调用 /v1/async/audio/transcriptions 接口（文件形式）
   */
  public GiteeTaskResponse asyncTranscriptions(File file, WhisperTranscriptionsRequest requestEntity) {
    String suffix = FilenameUtils.getSuffix(file.getName());
    String contentType = ContentTypeUtils.getContentType(suffix);
    RequestBody fileBody = RequestBody.create(file, MediaType.get(contentType));
    return asyncTranscriptions(fileBody, file.getName(), requestEntity);
  }

  /**
   * 调用 /v1/async/audio/transcriptions 接口（字节数组形式）
   */
  public GiteeTaskResponse asyncTranscriptions(byte[] data, String filename, WhisperTranscriptionsRequest requestEntity) {
    String suffix = FilenameUtils.getSuffix(filename);
    String contentType = ContentTypeUtils.getContentType(suffix);
    RequestBody fileBody = RequestBody.create(data, MediaType.get(contentType));
    return asyncTranscriptions(fileBody, filename, requestEntity);
  }

  /**
   * 内部方法：构造并发送文档解析请求
   */
  private GiteeTaskResponse parseDocument(RequestBody fileBody, String filename, GiteeDocumentParseRequest requestEntity) {
    String url = baseUrl + "/v1/async/documents/parse";

    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
        //
        .addFormDataPart("file", filename, fileBody)
        //
        .addFormDataPart("model", requestEntity.getModel());

    if (requestEntity.getInclude_image() != null) {
      bodyBuilder.addFormDataPart("include_image", requestEntity.getInclude_image().toString());
    }
    if (requestEntity.getInclude_image_base64() != null) {
      bodyBuilder.addFormDataPart("include_image_base64", requestEntity.getInclude_image_base64().toString());
    }
    if (requestEntity.getEnd_pages() != null) {
      bodyBuilder.addFormDataPart("end_pages", requestEntity.getEnd_pages().toString());
    }
    if (requestEntity.getOutput_format() != null) {
      bodyBuilder.addFormDataPart("output_format", requestEntity.getOutput_format());
    }
    if (requestEntity.getPrompt() != null && !requestEntity.getPrompt().trim().isEmpty()) {
      bodyBuilder.addFormDataPart("prompt", requestEntity.getPrompt());
    }

    Request request = new Request.Builder().url(url)
        //
        .addHeader("Authorization", "Bearer " + apiKey).addHeader("X-Failover-Enabled", "true")
        //
        .post(bodyBuilder.build()).build();

    return execute(request);
  }

  /**
   * 内部方法：构造并发送异步音频转写请求
   */
  private GiteeTaskResponse asyncTranscriptions(RequestBody fileBody, String filename,
      WhisperTranscriptionsRequest requestEntity) {
    String url = baseUrl + "/v1/async/audio/transcriptions";

    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
        //
        .addFormDataPart("file", filename, fileBody)
        //
        .addFormDataPart("model", requestEntity.getModel());

    if (requestEntity.getResponse_format() != null) {
      bodyBuilder.addFormDataPart("response_format", requestEntity.getResponse_format());
    }
    if (requestEntity.getPrompt() != null) {
      bodyBuilder.addFormDataPart("prompt", requestEntity.getPrompt());
    }
    if (requestEntity.getLanguage() != null) {
      bodyBuilder.addFormDataPart("language", requestEntity.getLanguage());
    }
    if (requestEntity.getTemperature() != null) {
      bodyBuilder.addFormDataPart("temperature", requestEntity.getTemperature().toString());
    }
    if (requestEntity.getStream() != null) {
      bodyBuilder.addFormDataPart("stream", requestEntity.getStream().toString());
    }

    Request request = new Request.Builder().url(url)
        //
        .addHeader("Authorization", "Bearer " + apiKey).addHeader("X-Failover-Enabled", "true")
        //
        .post(bodyBuilder.build()).build();

    return execute(request);
  }

  /**
   * 统一执行 HTTP 请求并解析为 GiteeTaskResponse
   */
  private GiteeTaskResponse execute(Request request) {
    return execute(request, GiteeTaskResponse.class);
  }

  private <T> T execute(Request request, Class<T> responseType) {
    Call call = httpClient.newCall(request);
    try (Response response = call.execute()) {
      if (response.isSuccessful()) {
        String json = response.body().string();
        return JsonUtils.parse(json, responseType);
      } else {
        String msg = "code:" + response.code() + ",body:" + response.body().string();
        throw new RuntimeException(msg);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static ChatModelResponse getModels() {
    String url = EnvUtils.get(GiteeConst.GITEE_API_URL_KEY, GiteeConst.API_PREFIX_URL);
    String key = EnvUtils.get(GiteeConst.GITEE_API_KEY);
    return OpenAiClient.getModels(url, key);

  }

  public static ChatModelResponse getModels(String apiKey) {
    return OpenAiClient.getModels(GiteeConst.API_PREFIX_URL, apiKey);
  }

  public static ResponseVo transcriptions(File file, WhisperTranscriptionsRequest entity) {
    String url = EnvUtils.get(GiteeConst.GITEE_API_URL_KEY, GiteeConst.API_PREFIX_URL);
    String key = EnvUtils.get(GiteeConst.GITEE_API_KEY);
    return WhisperClient.transcriptions(url, key, file, entity);
  }

  public static GiteeTaskResponse asyncAudioTranscriptions(File file, WhisperTranscriptionsRequest entity) {
    String key = EnvUtils.get(GiteeConst.GITEE_API_KEY);
    String baseUrl = EnvUtils.get(GiteeConst.GITEE_API_URL_KEY, GiteeConst.API_PREFIX_URL).replaceFirst("/v1$", "");
    return new GiteeClient(key, baseUrl).asyncTranscriptions(file, entity);
  }

}
