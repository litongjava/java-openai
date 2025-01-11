package com.litongjava.textin;

/**
 * https://www.textin.com/document/pdf_to_markdown
 */
import java.io.IOException;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TextinOcrClient {
  private static final MediaType MEDIA_TYPE_OCTET_STREAM = MediaType.parse("application/octet-stream");
  private static final OkHttpClient client = OkHttpClientPool.get3600HttpClient();

  /**
   * @param pdfFile
   * @param params
   * @return
   */
  public static PdfToMarkdownResponse convertPdfToMarkdown(byte[] pdfFile, PdfToMarkdownParams params) {
    String url = EnvUtils.getStr("x_ti_api_url");
    if (url == null) {
      url = TextinOcrConstants.API_URL;
    }
    String appId = EnvUtils.getStr("x_ti_app_id");
    String appSecret = EnvUtils.getStr("x_ti_secret_code");
    return convertPdfToMarkdown(url, appId, appSecret, pdfFile, params);
  }

  /**
   * @param url
   * @param appId
   * @param appSecret
   * @param pdfFile
   * @param params
   * @return
   */
  public static PdfToMarkdownResponse convertPdfToMarkdown(String url, String appId, String appSecret,
      //
      byte[] pdfFile, PdfToMarkdownParams params) {
    // 构造url
    url = buildUrl(url, params);

    String responseBody = convertPdfToMarkdown(url, appId, appSecret, pdfFile);
    return JsonUtils.parse(responseBody, PdfToMarkdownResponse.class);
  }

  public static String convertPdfToMarkdown(String url, String appId, String appSecret, byte[] pdfFile) {
    RequestBody body = RequestBody.create(pdfFile,MEDIA_TYPE_OCTET_STREAM);

    Request request = new Request.Builder().url(url).post(body)
        //
        .addHeader("x-ti-app-id", appId).addHeader("x-ti-secret-code", appSecret).build();

    String responseBody = null;
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new RuntimeException("Unexpected code:" + response + " body:" + response.body().string());
      }

      responseBody = response.body().string();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return responseBody;
  }

  private static String buildUrl(String url, PdfToMarkdownParams params) {
    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

    if (params.getPdfPwd() != null) {
      urlBuilder.addQueryParameter("pdf_pwd", params.getPdfPwd());
    }
    if (params.getDpi() != null) {
      urlBuilder.addQueryParameter("dpi", params.getDpi().toString());
    }
    if (params.getPageStart() != null) {
      urlBuilder.addQueryParameter("page_start", params.getPageStart().toString());
    }
    if (params.getPageCount() != null) {
      urlBuilder.addQueryParameter("page_count", params.getPageCount().toString());
    }
    if (params.getApplyDocumentTree() != null) {
      urlBuilder.addQueryParameter("apply_document_tree", params.getApplyDocumentTree().toString());
    }
    if (params.getMarkdownDetails() != null) {
      urlBuilder.addQueryParameter("markdown_details", params.getMarkdownDetails());
    }
    if (params.getTableFlavor() != null) {
      urlBuilder.addQueryParameter("table_flavor", params.getTableFlavor());
    }
    if (params.getGetImage() != null) {
      urlBuilder.addQueryParameter("get_image", params.getGetImage());
    }
    if (params.getParseMode() != null) {
      urlBuilder.addQueryParameter("parse_mode", params.getParseMode());
    }

    return urlBuilder.build().toString();
  }
}
