package com.litongjava.libreoffice;

import java.io.IOException;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LibreOfficeClient {

  public static final String LIBRE_API_BASE = "https://java-libreoffice-server.fly.dev";

  /**
   * Converts a supported document to PDF using the LibreOffice API.
   *
   * Supported formats: DOC, DOCX, PPT, PPTX, XLS, XLSX, ODT, ODS, ODP
   *
   * @param inputBytes The byte array of the input file to be converted.
   * @param fileName   The name of the input file, including its extension (e.g., "document.pptx").
   * @return ResponseVo containing the API response, including headers and the resulting PDF bytes or error message.
   */
  public static ResponseVo convertToPdf(byte[] inputBytes, String fileName) {
    // Retrieve the API base URL from environment variables or use the default
    String apiBase = EnvUtils.get("LIBRE_API_BASE", LIBRE_API_BASE);
    String serverUrl = apiBase + "/api/convert/pdf";

    // Create the request body for the file
    RequestBody fileBody = RequestBody.create(inputBytes);

    // Build the multipart/form-data request body
    MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();

    // Build the HTTP request
    Request request = new Request.Builder().url(serverUrl).post(requestBody).build();

    // Execute the request using a pooled OkHttpClient instance
    Call call = OkHttpClientPool.getHttpClient().newCall(request);
    try (Response response = call.execute()) {
      Headers headers = response.headers();
      if (response.isSuccessful()) {
        // Read the response body as bytes (PDF content)
        byte[] pdfBytes = response.body().bytes();
        return ResponseVo.ok(headers, pdfBytes);
      } else {
        // Read the error message from the response body
        String errorBody = response.body().string();
        return ResponseVo.fail(headers, errorBody);
      }
    } catch (IOException e) {
      // Handle exceptions such as network failures
      throw new RuntimeException("Failed to convert document to PDF", e);
    }
  }

}