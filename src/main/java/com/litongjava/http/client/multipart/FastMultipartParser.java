package com.litongjava.http.client.multipart;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class FastMultipartParser {

  private static final byte[] CRLF = new byte[] { '\r', '\n' };
  private static final byte[] HEADER_SEP = new byte[] { '\r', '\n', '\r', '\n' };

  public static void parse(String contentType, byte[] data, PartConsumer consumer) {
    String boundary = extractBoundary(contentType);
    if (boundary == null || boundary.isEmpty()) {
      throw new IllegalArgumentException("Missing boundary in Content-Type: " + contentType);
    }

    byte[] b0 = ("--" + boundary).getBytes(StandardCharsets.ISO_8859_1); // --boundary
    byte[] b1 = ("\r\n--" + boundary).getBytes(StandardCharsets.ISO_8859_1); // \r\n--boundary
    byte[] bEnd = ("--" + boundary + "--").getBytes(StandardCharsets.ISO_8859_1); // --boundary--

    int pos = 0;

    // 找第一个 boundary（可能在开头）
    int first = indexOf(data, b0, 0);
    if (first < 0)
      return;

    pos = first;

    while (true) {
      // 读 boundary 行结束 CRLF
      int lineEnd = indexOf(data, CRLF, pos);
      if (lineEnd < 0)
        return;

      // 判断是否结束 boundary：--boundary--
      boolean isEnd = startsWith(data, pos, bEnd);
      if (isEnd)
        return;

      // headers: 从 lineEnd+2 到 \r\n\r\n
      int headersStart = lineEnd + 2;
      int headersEnd = indexOf(data, HEADER_SEP, headersStart);
      if (headersEnd < 0)
        return;

      // 解析 name（只解 header 那一小段）
      String headers = new String(data, headersStart, headersEnd - headersStart, StandardCharsets.ISO_8859_1);
      String name = extractName(headers);

      int bodyStart = headersEnd + 4;

      // 下一个 boundary：通常是 \r\n--boundary
      int next = indexOf(data, b1, bodyStart);
      if (next < 0)
        return;

      int bodyEnd = next; // 不含 \r\n
      // bodyEnd 前面已经是正文末尾；按规范正文末尾会有 CRLF，在这里剔除
      if (bodyEnd >= 2 && data[bodyEnd - 2] == '\r' && data[bodyEnd - 1] == '\n') {
        bodyEnd -= 2;
      }

      if (name != null && !name.isEmpty()) {
        consumer.accept(name, data, bodyStart, bodyEnd - bodyStart);
      }

      // 跳到 next boundary 的 "--boundary" 开头（next 指向 \r\n--boundary 的 \r）
      pos = next + 2; // 跳过前导 \r\n，落在 --boundary
    }
  }

  private static boolean startsWith(byte[] src, int off, byte[] pat) {
    if (off + pat.length > src.length)
      return false;
    for (int i = 0; i < pat.length; i++) {
      if (src[off + i] != pat[i])
        return false;
    }
    return true;
  }

  // 朴素搜索：足够快（boundary 通常不长）；后面可替换为 KMP/BM
  private static int indexOf(byte[] src, byte[] pat, int from) {
    if (pat.length == 0)
      return from;
    int max = src.length - pat.length;
    for (int i = Math.max(0, from); i <= max; i++) {
      if (src[i] != pat[0])
        continue;
      int j = 1;
      while (j < pat.length && src[i + j] == pat[j])
        j++;
      if (j == pat.length)
        return i;
    }
    return -1;
  }

  private static String extractBoundary(String contentType) {
    String lower = contentType.toLowerCase(Locale.ROOT);
    int idx = lower.indexOf("boundary=");
    if (idx < 0)
      return null;

    String b = contentType.substring(idx + "boundary=".length()).trim();
    if (b.startsWith("\"") && b.endsWith("\"") && b.length() >= 2) {
      b = b.substring(1, b.length() - 1);
    }
    int semi = b.indexOf(';');
    if (semi > 0)
      b = b.substring(0, semi).trim();
    return b;
  }

  private static String extractName(String headers) {
    String lower = headers.toLowerCase(Locale.ROOT);
    int cd = lower.indexOf("content-disposition:");
    if (cd < 0)
      return null;

    int nameIdx = lower.indexOf("name=", cd);
    if (nameIdx < 0)
      return null;

    String after = headers.substring(nameIdx + "name=".length()).trim();
    if (after.startsWith("\"")) {
      int end = after.indexOf('"', 1);
      if (end > 1)
        return after.substring(1, end);
    } else {
      int end = after.indexOf(';');
      return (end > 0) ? after.substring(0, end).trim() : after.trim();
    }
    return null;
  }
}
