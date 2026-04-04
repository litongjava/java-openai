package com.litongjava.openai.whisper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.litongjava.aiapi.AiApiConst;
import com.litongjava.consts.ModelPlatformName;
import com.litongjava.openai.consts.OpenAiConst;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.FilenameUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import nexus.io.model.http.response.ResponseVo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WhisperClient {

  public static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);
  public static final String OPENAI_API_KEY = EnvUtils.get("OPENAI_API_KEY");

  public static final String AIAPI_API_URL = EnvUtils.get("AIAPI_API_URL", AiApiConst.V1_BASE_URL);
  public static final String AIAPI_API_KEY = EnvUtils.get("AIAPI_API_KEY");

  public static WhisperTranscriptionResponse transcriptToJson(String platform, File file) {
    try {
      String filename = file.getName();
      byte[] audioBytes = Files.readAllBytes(file.toPath());
      return transcriptToJson(platform, filename, audioBytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static WhisperTranscriptionResponse transcriptToJson(String platform, String filename, byte[] audioBytes) {
    if (ModelPlatformName.AIAPI.equals(platform)) {
      return transcriptToJson(AIAPI_API_URL, AIAPI_API_KEY, filename, audioBytes);
    } else {
      return transcriptToJson(OPENAI_API_URL, OPENAI_API_KEY, filename, audioBytes);
    }
  }

  private static WhisperTranscriptionResponse transcriptToJson(String apiPrefixUrl, String apiKey, String filename, byte[] audioBytes) {
    ResponseVo responseVo = transcriptions(apiPrefixUrl, apiKey, filename, audioBytes, WhisperResponseFormat.json);
    String bodyString = responseVo.getBodyString();
    if(responseVo.isOk()) {
      return JsonUtils.parse(bodyString, WhisperTranscriptionResponse.class);
    }
    
    WhisperTranscriptionResponse whisperTranscriptionResponse = new WhisperTranscriptionResponse();
    whisperTranscriptionResponse.setRasResponse(bodyString);
    return whisperTranscriptionResponse;
  }

  /**
   * Transcribes the given audio file using the default API key from environment
   * variables.
   *
   * @param filename       the name of the audio file
   * @param audioBytes     the content of the audio file in bytes
   * @param responseFormat the desired response format (e.g., json)
   * @return a ResponseVo containing the transcription result or error information
   */
  public static ResponseVo transcriptions(String filename, byte[] audioBytes, String responseFormat) {
    // Get API key from environment variables
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return transcriptions(apiKey, filename, audioBytes, responseFormat);
  }

  public static ResponseVo transcriptions(String filename, byte[] audioBytes, String responseFormat, String format) {
    // Get API key from environment variables
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return transcriptions(apiKey, filename, audioBytes, responseFormat, format);
  }

  public static ResponseVo transcriptions(File file, String responseFormat) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return transcriptions(apiKey, file, responseFormat);
  }

  public static ResponseVo transcriptions(File file, String responseFormat, String prompt) {
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return transcriptions(apiKey, file, responseFormat, prompt);
  }

  public static ResponseVo transcriptions(String apiKey, File file, String responseFormat) {
    String apiPrefixUrl = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);
    return transcriptions(apiPrefixUrl, apiKey, file, responseFormat);
  }

  public static ResponseVo transcriptions(String apiKey, File file, String responseFormat, String prompt) {
    String apiPrefixUrl = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);
    return transcriptions(apiPrefixUrl, apiKey, file, responseFormat, prompt);
  }

  /**
   * Transcribes the given audio file using the provided API key.
   *
   * @param apiKey         the API key to use for authentication
   * @param filename       the name of the audio file
   * @param audioBytes     the content of the audio file in bytes
   * @param responseFormat the desired response format (e.g., json)
   * @return a ResponseVo containing the transcription result or error information
   */
  public static ResponseVo transcriptions(String apiKey, String filename, byte[] audioBytes, String responseFormat) {
    // Get base API URL from environment or use default constant
    String apiPrefixUrl = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);

    return transcriptions(apiPrefixUrl, apiKey, filename, audioBytes, responseFormat);
  }

  public static ResponseVo transcriptions(String apiKey, String filename, byte[] audioBytes, String responseFormat,
      String prompt) {
    // Get base API URL from environment or use default constant
    String apiPrefixUrl = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);

    WhisperTranscriptionsRequest entity = new WhisperTranscriptionsRequest(responseFormat, prompt);
    return transcriptions(apiPrefixUrl, apiKey, filename, audioBytes, entity);
  }

  /**
   * Transcribes the given audio file using the provided base URL and API key.
   *
   * @param baseUrl        the base URL for the API
   * @param apiKey         the API key for authentication
   * @param filename       the name of the audio file
   * @param audioBytes     the content of the audio file in bytes
   * @param responseFormat the desired response format (e.g., json)
   * @return a ResponseVo containing the transcription result or error information
   */
  public static ResponseVo transcriptions(String baseUrl, String apiKey, String filename, byte[] audioBytes,
      String responseFormat) {
    // Determine the content type based on the file extension

    String contentType = ContentTypeUtils.getContentType(FilenameUtils.getSuffix(filename));
    RequestBody fileBody = RequestBody.create(audioBytes, MediaType.get(contentType));
    // Build the HTTP header with the API key for authorization

    return transcriptions(baseUrl, apiKey, filename, fileBody, responseFormat);
  }

  public static ResponseVo transcriptions(String baseUrl, String apiKey, String filename, byte[] audioBytes,
      WhisperTranscriptionsRequest entity) {
    // Determine the content type based on the file extension

    String contentType = ContentTypeUtils.getContentType(FilenameUtils.getSuffix(filename));
    RequestBody fileBody = RequestBody.create(audioBytes, MediaType.get(contentType));
    // Build the HTTP header with the API key for authorization

    return transcriptions(baseUrl, apiKey, filename, fileBody, entity);
  }

  public static ResponseVo transcriptions(String baseUrl, String apiKey, File file, String responseFormat) {
    // Determine the content type based on the file extension
    String filename = file.getName();
    String contentType = ContentTypeUtils.getContentType(FilenameUtils.getSuffix(filename));
    RequestBody fileBody = RequestBody.create(file, MediaType.get(contentType));
    // Build the HTTP header with the API key for authorization
    return transcriptions(baseUrl, apiKey, filename, fileBody, responseFormat);
  }

  public static ResponseVo transcriptions(String baseUrl, String apiKey, File file, String responseFormat,
      String prompt) {
    // Determine the content type based on the file extension
    String filename = file.getName();
    String contentType = ContentTypeUtils.getContentType(FilenameUtils.getSuffix(filename));
    RequestBody fileBody = RequestBody.create(file, MediaType.get(contentType));
    // Build the HTTP header with the API key for authorization
    WhisperTranscriptionsRequest requestEntity = new WhisperTranscriptionsRequest(responseFormat, prompt);
    return transcriptions(baseUrl, apiKey, filename, fileBody, requestEntity);
  }

  public static ResponseVo transcriptions(String baseUrl, String apiKey, String filename, RequestBody fileBody,
      String responseFormat) {
    return transcriptions(baseUrl, apiKey, filename, fileBody, new WhisperTranscriptionsRequest(responseFormat));
  }

  public static ResponseVo transcriptions(String baseUrl, String apiKey, String filename, RequestBody fileBody,
      WhisperTranscriptionsRequest requestEntity) {
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", "Bearer " + apiKey);

    // Build the multipart form data
    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
    bodyBuilder.setType(MultipartBody.FORM);

    String model = requestEntity.getModel();
    String response_format = requestEntity.getResponse_format();
    String prompt = requestEntity.getPrompt();
    Float temperature = requestEntity.getTemperature();
    Boolean stream = requestEntity.getStream();

    // Add response format and model parameters
    bodyBuilder.addFormDataPart("file", filename, fileBody);
    bodyBuilder.addFormDataPart("response_format", response_format);
    bodyBuilder.addFormDataPart("model", model);
    if (prompt != null) {
      bodyBuilder.addFormDataPart("prompt", prompt);
    }

    if (temperature != null) {
      bodyBuilder.addFormDataPart("temperature", temperature.toString());
    }
    if (stream != null) {
      bodyBuilder.addFormDataPart("stream", stream.toString());
    }

    // Add the audio file to the form

    MultipartBody formBody = bodyBuilder.build();
    return execute(baseUrl, header, formBody);
  }

  private static ResponseVo execute(String baseUrl, Map<String, String> header, MultipartBody formBody) {
    // Construct the complete URL
    String url = baseUrl + "/audio/transcriptions";

    // Build the HTTP request and include the header information
    Request.Builder requestBuilder = new Request.Builder().url(url).post(formBody);
    // Add each header from the map
    for (Map.Entry<String, String> entry : header.entrySet()) {
      requestBuilder.addHeader(entry.getKey(), entry.getValue());
    }
    Request request = requestBuilder.build();

    // Obtain an HTTP client from the pool
    OkHttpClient httpClient = OkHttpClientPool.get600HttpClient();

    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      int code = response.code();
      if (response.isSuccessful()) {
        // Return success response with the response body
        return ResponseVo.ok(responseBody);
      } else {
        // Return failure response with code and response body
        return ResponseVo.fail(code, responseBody);
      }
    } catch (IOException e) {
      // Wrap and rethrow any IO exceptions
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
