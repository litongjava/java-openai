package nexus.io.linux;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.litongjava.tio.utils.commandline.ProcessResult;

public class MultipartProcessResultParser {

  public static ProcessResult parse(String contentType, byte[] bodyBytes) {
    String boundary = extractBoundary(contentType);
    if (boundary == null || boundary.isEmpty()) {
      throw new IllegalArgumentException("Missing boundary in Content-Type: " + contentType);
    }

    String boundaryLine = "--" + boundary;
    String endBoundaryLine = boundaryLine + "--";

    // 用 ISO-8859-1 把字节映射到字符，保证 0-255 不丢失）
    String raw = new String(bodyBytes, StandardCharsets.ISO_8859_1);

    ProcessResult r = new ProcessResult();

    int pos = 0;
    while (true) {
      int bStart = raw.indexOf(boundaryLine, pos);
      if (bStart < 0)
        break;

      int lineEnd = raw.indexOf("\r\n", bStart);
      if (lineEnd < 0)
        break;

      String firstLine = raw.substring(bStart, lineEnd);
      if (firstLine.equals(endBoundaryLine))
        break;

      int headersEnd = raw.indexOf("\r\n\r\n", lineEnd + 2);
      if (headersEnd < 0)
        break;

      String headers = raw.substring(lineEnd + 2, headersEnd);
      String name = extractName(headers);
      if (name == null || name.isEmpty()) {
        pos = headersEnd + 4;
        continue;
      }

      int partBodyStart = headersEnd + 4;
      int nextBoundary = raw.indexOf("\r\n" + boundaryLine, partBodyStart);
      if (nextBoundary < 0)
        break;

      // part body（去掉末尾 CRLF）
      int partBodyEnd = nextBoundary;
      if (partBodyEnd - 2 >= partBodyStart && raw.startsWith("\r\n", partBodyEnd - 2)) {
        partBodyEnd -= 2;
      }

      String partBodyIso = raw.substring(partBodyStart, partBodyEnd);
      // 服务端字段值是 UTF-8 文本，所以这里再从 ISO-8859-1 转回原字节再按 UTF-8 解
      byte[] partBytes = partBodyIso.getBytes(StandardCharsets.ISO_8859_1);
      String value = new String(partBytes, StandardCharsets.UTF_8);

      applyField(r, name, value);

      pos = nextBoundary + 2; // 跳过前面的 \r\n
    }

    return r;
  }

  private static String extractBoundary(String contentType) {
    // Content-Type: multipart/mixed; boundary=xxx
    String lower = contentType.toLowerCase(Locale.ROOT);
    int idx = lower.indexOf("boundary=");
    if (idx < 0)
      return null;

    String b = contentType.substring(idx + "boundary=".length()).trim();
    // 可能带引号
    if (b.startsWith("\"") && b.endsWith("\"") && b.length() >= 2) {
      b = b.substring(1, b.length() - 1);
    }
    // 后面可能还有分号参数
    int semi = b.indexOf(';');
    if (semi > 0)
      b = b.substring(0, semi).trim();
    return b;
  }

  private static String extractName(String headers) {
    // 找 Content-Disposition: ... name="xxx"
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

  private static void applyField(ProcessResult r, String name, String value) {
    switch (name) {
    case "exit_code":
      r.setExitCode(parseInt(value));
      break;
    case "std_out":
      r.setStdOut(value);
      break;
    case "std_err":
      r.setStdErr(value);
      break;
    case "elapsed":
      r.setElapsed(parseLongObj(value));
      break;
    case "cached":
      r.setCached(parseBoolObj(value));
      break;
    case "prt":
      r.setPrt(parseLongObj(value));
      break;
    case "session_id":
      r.setSessionId(parseLongObj(value));
      break;
    case "task_id":
      r.setTaskId(parseLongObj(value));
      break;
    case "sources":
      r.setSources(value);
      break;
    case "execute_code":
      r.setExecuteCode(value);
      break;
    case "output":
      r.setOutput(value);
      break;
    case "json":
      r.setJson(value);
      break;
    case "message":
      r.setMessage(value);
      break;
    case "text":
      r.setText(value);
      break;
    case "subtitle":
      r.setSubtitle(value);
      break;
    case "image":
      r.setImage(value);
      break;
    case "audio":
      r.setAudio(value);
      break;
    case "video":
      r.setVideo(value);
      break;
    case "hls_url":
      r.setHlsUrl(value);
      break;
    case "ppt":
      r.setPpt(value);
      break;
    case "video_length":
      r.setVideo_length(parseDoubleObj(value));
      break;
    default:
      // 未列入的字段先忽略（以后加新字段再补）
      break;
    }
  }

  private static int parseInt(String s) {
    try {
      return Integer.parseInt(s.trim());
    } catch (Exception e) {
      return 0;
    }
  }

  private static Long parseLongObj(String s) {
    try {
      return Long.valueOf(s.trim());
    } catch (Exception e) {
      return null;
    }
  }

  private static Boolean parseBoolObj(String s) {
    String t = s.trim().toLowerCase(Locale.ROOT);
    if (t.isEmpty())
      return null;
    if ("true".equals(t) || "1".equals(t))
      return Boolean.TRUE;
    if ("false".equals(t) || "0".equals(t))
      return Boolean.FALSE;
    return null;
  }

  private static Double parseDoubleObj(String s) {
    try {
      return Double.valueOf(s.trim());
    } catch (Exception e) {
      return null;
    }
  }
}
